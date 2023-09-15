package kitchenpos.products.tobe.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public ProductEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void changePrice(ProductPriceChangeEvent event) {
        eventPublisher.publishEvent(event);
    }
}
