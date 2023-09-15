package kitchenpos.eatinorders.domain.tobe;

import org.springframework.stereotype.Component;

@Component
public class OrderManager {

    private OrderRepository orderRepository;

    public OrderManager(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean isExistNotComplateOrder(OrderTable orderTable) {
        return orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED);
    }
}
