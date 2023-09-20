package kitchenpos.eatinorders.domain;

import org.springframework.stereotype.Component;

@Component
public class EatInOrderAcceptService {

    private final OrderRepository orderRepository;

    public EatInOrderAcceptService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order accept(Order order) {
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }
}
