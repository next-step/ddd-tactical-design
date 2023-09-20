package kitchenpos.eatinorders.domain;

import org.springframework.stereotype.Component;

@Component
public class EatInOrderServeService {

    private final OrderRepository orderRepository;

    public EatInOrderServeService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order serve(Order order) {
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.SERVED);
        return orderRepository.save(order);
    }
}
