package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangeRequest {
    private final UUID productId;
    private final BigDecimal productPrice;

    public ProductPriceChangeRequest(UUID productId, BigDecimal productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}
