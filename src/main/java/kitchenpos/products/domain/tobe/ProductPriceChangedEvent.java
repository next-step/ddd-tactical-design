package kitchenpos.products.domain.tobe;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangedEvent extends ApplicationEvent {
    private UUID productId;
    private BigDecimal price;

    public ProductPriceChangedEvent(Object source, UUID productId, BigDecimal price) {
        super(source);
        this.productId = productId;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
