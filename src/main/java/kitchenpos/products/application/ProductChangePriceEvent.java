package kitchenpos.products.application;

import java.util.UUID;

public record ProductChangePriceEvent(UUID productId) {
    public static ProductChangePriceEvent from(UUID productId) {
        return new ProductChangePriceEvent(productId);
    }
}
