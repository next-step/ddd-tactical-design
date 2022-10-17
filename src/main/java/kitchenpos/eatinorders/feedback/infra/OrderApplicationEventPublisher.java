package kitchenpos.eatinorders.feedback.infra;

import kitchenpos.eatinorders.feedback.application.OrderEventPublisher;
import kitchenpos.eatinorders.feedback.event.OrderCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderApplicationEventPublisher implements OrderEventPublisher {
    private final ApplicationEventPublisher publisher;

    public OrderApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void complete(Long orderTableId) {
        publisher.publishEvent(new OrderCompleteEvent(this, orderTableId));
    }
}
