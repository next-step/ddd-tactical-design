package kitchenpos.tobe;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.tobe.domain.model.Product;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(name, BigDecimal.valueOf(price));
    }
}
