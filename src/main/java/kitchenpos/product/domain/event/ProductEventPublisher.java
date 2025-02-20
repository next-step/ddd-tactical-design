package kitchenpos.product.domain.event;

import java.util.UUID;
import kitchenpos.global.event.ProductPriceChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public ProductEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishProductPriceChangedEvent(UUID productId) {
        eventPublisher.publishEvent(new ProductPriceChangedEvent(productId));
    }
}
