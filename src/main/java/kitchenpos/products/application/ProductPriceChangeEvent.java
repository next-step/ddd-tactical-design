package kitchenpos.products.application;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangeEvent extends ApplicationEvent {

    private final BigDecimal price;
    private final UUID productId;

    public ProductPriceChangeEvent(Object source, BigDecimal price, UUID productId) {
        super(source);
        this.price = price;
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getProductId() {
        return productId;
    }
}
