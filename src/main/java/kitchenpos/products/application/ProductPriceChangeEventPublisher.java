package kitchenpos.products.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class ProductPriceChangeEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductPriceChangeEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(BigDecimal price, UUID productId) {
        applicationEventPublisher.publishEvent(new ProductPriceChangeEvent(this, price, productId));
    }

}
