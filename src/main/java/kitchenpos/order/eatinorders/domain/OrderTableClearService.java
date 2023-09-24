package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;


@Component
public class OrderTableClearService {

    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableClearService(final OrderTableRepository orderTableRepository, OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    public OrderTable clear(final Order requestOrder) {
        OrderTable orderTable = orderTableRepository.findById(requestOrder.getOrderTableId()).orElseThrow(NoSuchElementException::new);
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
        return orderTableRepository.save(orderTable);
    }

    public OrderTable clear(final UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        return orderTableRepository.save(orderTable.clear());
    }
}
