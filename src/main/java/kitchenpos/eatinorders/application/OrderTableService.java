package kitchenpos.eatinorders.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.OrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ui.request.OrderTableCreateRequest;
import kitchenpos.eatinorders.ui.response.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableService(OrderTableRepository orderTableRepository, OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(OrderTableCreateRequest request) {
        OrderTable orderTable = new OrderTable(UUID.randomUUID(), request.getName());
        return OrderTableResponse.from(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        OrderTable orderTable = findOrderTableById(orderTableId);
        orderTable.sit();
        return OrderTableResponse.from(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        OrderTable orderTable = findOrderTableById(orderTableId);
        validateOrderCompleted(orderTable);
        orderTable.clear();
        return OrderTableResponse.from(orderTable);
    }

    private void validateOrderCompleted(OrderTable orderTable) {
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException("주문이 완료되지 않아 테이블을 정리할 수 없습니다.");
        }
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final OrderTableChangeNumberOfGuestsRequest request) {
        final OrderTable orderTable = findOrderTableById(orderTableId);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return OrderTableResponse.from(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private OrderTable findOrderTableById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
    }
}
