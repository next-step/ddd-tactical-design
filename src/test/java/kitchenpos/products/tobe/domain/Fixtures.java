package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Fixtures {

    public static Product product(final UUID id, final String name, final BigDecimal price) {
        return new Product(id, new DisplayedName(name), new Price(price));
    }

    public static Product product() {
        return product(UUID.randomUUID(), "후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product product(final UUID id) {
        return product(id, "후라이드", BigDecimal.valueOf(16_000L));
    }

    public static Product product(final String name) {
        return product(UUID.randomUUID(), name, BigDecimal.valueOf(16_000L));
    }

    public static Product product(final BigDecimal price) {
        return product(UUID.randomUUID(), "후라이드", price);
    }
}
