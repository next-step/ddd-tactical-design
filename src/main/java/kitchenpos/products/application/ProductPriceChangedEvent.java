package kitchenpos.products.application;

import kitchenpos.support.domain.ProductPrice;

import java.util.UUID;

public record ProductPriceChangedEvent(UUID productId, ProductPrice price) {
    public static ProductPriceChangedEvent of(UUID productId, ProductPrice price) {
        return new ProductPriceChangedEvent(productId, price);
    }
}
