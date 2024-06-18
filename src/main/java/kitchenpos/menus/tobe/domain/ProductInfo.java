package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfo(
        UUID productId,
        BigDecimal price
) {
    public static ProductInfo is(UUID productId, BigDecimal price) {
        return new ProductInfo(productId, price);
    }
}
