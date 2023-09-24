package kitchenpos.eatinorders.ordertable.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.order.domain.EatInOrderRepository;
import kitchenpos.eatinorders.order.domain.OrderStatus;
import kitchenpos.eatinorders.ordertable.application.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ordertable.application.dto.CreateOrderTableRequest;
import kitchenpos.eatinorders.ordertable.application.dto.OrderTableResponse;
import kitchenpos.eatinorders.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.domain.OrderTableRepository;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final EatInOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(final CreateOrderTableRequest request) {
        OrderTable orderTable = orderTableRepository.save(new OrderTable(UUID.randomUUID(), request.getName()));
        return new OrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.use();
        return new OrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clean();
        return new OrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestsRequest request) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return new OrderTableResponse(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll()
                .stream()
                .map(OrderTableResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private OrderTable findById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }
}
