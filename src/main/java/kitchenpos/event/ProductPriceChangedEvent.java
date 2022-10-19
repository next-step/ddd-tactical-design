package kitchenpos.event;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductPriceChangedEvent {

    private final UUID productId;
    private final BigDecimal productPrice;

    public ProductPriceChangedEvent(UUID productId, BigDecimal productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPriceChangedEvent that = (ProductPriceChangedEvent) o;
        return Objects.equals(productId, that.productId) && Objects.equals(
            productPrice,
            that.productPrice
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productPrice);
    }
}
