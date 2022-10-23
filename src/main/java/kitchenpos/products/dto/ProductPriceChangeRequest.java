package kitchenpos.products.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangeRequest {
    private UUID productId;
    private BigDecimal price;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
