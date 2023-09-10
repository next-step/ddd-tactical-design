package kitchenpos.products.publisher;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class ProductPriceChangedEvent extends ApplicationEvent {
    private UUID productId;

    public ProductPriceChangedEvent(Object source, UUID productId) {
        super(source);
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
