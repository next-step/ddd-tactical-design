package kitchenpos.product.tobe.application;

import java.util.UUID;

public record ProductPriceChangedEvent(UUID productId, long price) {
    public static ProductPriceChangedEvent of(UUID productId, long price) {
        return new ProductPriceChangedEvent(productId, price);
    }
}
