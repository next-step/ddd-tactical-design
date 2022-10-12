package kitchenpos.products.tobe.domain.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChangedPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public ProductPriceChangedPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(ProductPriceChangedEvent evt) {
        eventPublisher.publishEvent(evt);
    }
}
