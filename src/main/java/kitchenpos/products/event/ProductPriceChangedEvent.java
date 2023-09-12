package kitchenpos.products.event;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ProductPriceChangedEvent extends ApplicationEvent {
    private final UUID productId;
    private final BigDecimal changedPrice;


    public ProductPriceChangedEvent(UUID productId, BigDecimal changedPrice) {
        super(ProductPriceChangedEvent.class);

        this.productId = productId;
        this.changedPrice = changedPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getChangedPrice() {
        return changedPrice;
    }
}