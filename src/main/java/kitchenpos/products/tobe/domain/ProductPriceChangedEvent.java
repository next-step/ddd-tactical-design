package kitchenpos.products.tobe.domain;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class ProductPriceChangedEvent extends ApplicationEvent {

    private UUID productId;

    private ProductPrice productPrice;

    public ProductPriceChangedEvent(Object source, UUID productId, ProductPrice productPrice) {
        super(source);
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }
}
