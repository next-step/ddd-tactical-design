package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.*;
import kitchenpos.eatinorders.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NOT_EXIST_COMPLETE_ORDER;

@Service
public class OrderTableService {
    private final EatInOrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;

    public OrderTableService(final EatInOrderTableRepository orderTableRepository, final EatInOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableRequest request) {
        EatInOrderTable eatInOrderTable = EatInOrderTable.create(
                UUID.randomUUID(),
                OrderTableName.create(request.getName()),
                NumberOfGuests.ZERO,
                false);
        EatInOrderTable savedEatInOrderTable = orderTableRepository.save(eatInOrderTable);
        return OrderTableResponse.create(
                savedEatInOrderTable.getId(),
                savedEatInOrderTable.getNameValue(),
                savedEatInOrderTable.getNumberOfGuestsValue(),
                savedEatInOrderTable.isOccupied()
        );
    }

    @Transactional
    public boolean sit(final UUID orderTableId) {
        final EatInOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        return orderTable.isOccupied();
    }

    @Transactional
    public OrderTableClearResponse clear(final UUID orderTableId) {
        final EatInOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (isNotExistCompletedOrder(orderTable)) {
            throw new IllegalStateException(NOT_EXIST_COMPLETE_ORDER);
        }
        orderTable.clear();
        return OrderTableClearResponse.create(orderTable.getNumberOfGuestsValue(), orderTable.isOccupied());
    }

    @Transactional
    public OrderTableChangeNumberOfGuestsResponse changeNumberOfGuests(final UUID orderTableId, final OrderTableChangeNumberOfGuestsRequest request) {
        final EatInOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(NumberOfGuests.create(request.getNumberOfGuests()));
        return OrderTableChangeNumberOfGuestsResponse.create(
                orderTable.getId(), orderTable.getNameValue(), orderTable.getNumberOfGuestsValue());
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        List<EatInOrderTable> orderTables = orderTableRepository.findAll();
        return orderTables.stream()
                .map(m -> OrderTableResponse.create(m.getId(), m.getNameValue(), m.getNumberOfGuestsValue(), m.isOccupied()))
                .collect(Collectors.toList());
    }

    private boolean isNotExistCompletedOrder(EatInOrderTable orderTable) {
        return orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED);
    }
}
