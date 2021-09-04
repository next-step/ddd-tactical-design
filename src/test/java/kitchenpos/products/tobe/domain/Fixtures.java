package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Fixtures {

    public static Product PRODUCT_WITH_ALL(final UUID id, final String name, final BigDecimal price) {
        return new Product(id, new DisplayedName(name), new Price(price));
    }

    public static Product DEFAULT_PRODUCT() {
        return PRODUCT_WITH_ALL(UUID.randomUUID(), "후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product PRODUCT_WITH_ID(final UUID id) {
        return PRODUCT_WITH_ALL(id, "후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product PRODUCT_WITH_NAME(final String name) {
        return PRODUCT_WITH_ALL(UUID.randomUUID(), name, BigDecimal.valueOf(16_000L));
    }

    public static Product PRODUCT_WITH_PRICE(final BigDecimal price) {
        return PRODUCT_WITH_ALL(UUID.randomUUID(), "후라이드", price);
    }
}
