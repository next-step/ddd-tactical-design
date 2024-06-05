package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.CompletedOrderEvent;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EatInOrderCompletePolicy {
    private final EatInOrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    public EatInOrderCompletePolicy(EatInOrderRepository orderRepository, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public void complete(EatInOrder order) {
        order.complete();
        List<EatInOrder> orders = orderRepository.findAllByOrderTableId(order.getOrderTableId());
        boolean allOrderCompleted = orders.stream()
                .allMatch(thisOrder -> thisOrder.isComplete());

        if (allOrderCompleted) {
            publisher.publishEvent(new CompletedOrderEvent(order.getOrderTableId()));
        }
    }
}
