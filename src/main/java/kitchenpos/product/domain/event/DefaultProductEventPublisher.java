package kitchenpos.product.domain.event;

import kitchenpos.global.event.ProductEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DefaultProductEventPublisher implements ProductEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public DefaultProductEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(ProductEvent event) {
        eventPublisher.publishEvent(event);
    }
}
