package kitchenpos.common.event;

import java.util.Objects;
import java.util.UUID;

public class ProductPriceChangedEvent {

    private final UUID productId;
    private final Long price;

    public ProductPriceChangedEvent(UUID productId, Long price) {
        this.productId = productId;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPriceChangedEvent that = (ProductPriceChangedEvent) o;
        return Objects.equals(productId, that.productId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price);
    }
}
