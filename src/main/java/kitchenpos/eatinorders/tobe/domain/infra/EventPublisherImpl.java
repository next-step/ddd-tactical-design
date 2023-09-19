package kitchenpos.eatinorders.tobe.domain.infra;


import kitchenpos.eatinorders.tobe.domain.BaseEvent;
import kitchenpos.eatinorders.tobe.domain.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public EventPublisherImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(BaseEvent event) {
        eventPublisher.publishEvent(event);
    }
}
