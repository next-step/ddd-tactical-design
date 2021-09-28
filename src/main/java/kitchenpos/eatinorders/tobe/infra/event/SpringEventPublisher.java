package kitchenpos.eatinorders.tobe.infra.event;

import kitchenpos.eatinorders.tobe.domain.interfaces.event.EventPublisher;
import kitchenpos.eatinorders.tobe.domain.model.TableCleanedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishTableCleaningEvent(TableCleanedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
