package kitchenpos.products.application;

import kitchenpos.support.domain.ProductPrice;

import java.util.UUID;

public record ProductChangePriceEvent(UUID productId, ProductPrice price) {
    public static ProductChangePriceEvent from(UUID productId, ProductPrice price) {
        return new ProductChangePriceEvent(productId, price);
    }
}
