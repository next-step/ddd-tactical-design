package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.OrderCompleteEvent;
import kitchenpos.eatinorders.domain.OrderEventService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements OrderEventService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void notifyOrderComplete(OrderCompleteEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
