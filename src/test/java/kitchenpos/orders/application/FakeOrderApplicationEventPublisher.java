package kitchenpos.orders.application;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class FakeOrderApplicationEventPublisher implements ApplicationEventPublisher {
    private final OrderRepository orderRepository;

    public FakeOrderApplicationEventPublisher(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void publishEvent(Object event) {
        if (event instanceof OrderStatusChangeEvent) {
            OrderStatusChangeEvent orderStatusChangeEvent = (OrderStatusChangeEvent) event;
            orderRepository.findById(orderStatusChangeEvent.getOrderId())
                    .ifPresent(order -> {
                        order.chageStatus(orderStatusChangeEvent.getOrderStatus());
                        orderRepository.save(order);
                    });
        }

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
    }

}
