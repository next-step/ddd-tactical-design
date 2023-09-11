package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.dto.OrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final EatInOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.eatInOrderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableRequest request) {
        final OrderTable orderTable = OrderTable.of(request);
        OrderTable savedOrderTable = orderTableRepository.save(orderTable);
        return new OrderTableResponse(savedOrderTable.getId(), savedOrderTable.getName(), savedOrderTable.getNumberOfGuests(), savedOrderTable.isOccupied());
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        return new OrderTableResponse(orderTable.getId(), orderTable.getName(), orderTable.getNumberOfGuests(), orderTable.isOccupied());
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return new OrderTableResponse(orderTable.getId(), orderTable.getName(), orderTable.getNumberOfGuests(), orderTable.isOccupied());
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final OrderTableRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTable.changeNumberOfGuest(request.getNumberOfGuests());
        return new OrderTableResponse(orderTable.getId(), orderTable.getName(), orderTable.getNumberOfGuests(), orderTable.isOccupied());
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll().stream()
                .map(it -> new OrderTableResponse(it.getId(), it.getName(), it.getNumberOfGuests(), it.isOccupied()))
                .collect(Collectors.toList());
    }
}
