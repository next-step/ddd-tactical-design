package kitchenpos.products.event;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ProductPriceChangedEvent {

    private UUID productId;
    private BigDecimal productPrice;

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public ProductPriceChangedEvent(UUID productId,BigDecimal productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

}
