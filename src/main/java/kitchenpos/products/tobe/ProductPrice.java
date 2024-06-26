package kitchenpos.products.tobe;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPrice {

    private UUID productId;
    private BigDecimal price;

    public ProductPrice(UUID id, BigDecimal price) {
        this.productId = id;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
