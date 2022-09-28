package kitchenpos.tobe;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.Product;

public class Fixtures {

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(String name, long price) {
        return new Product(name, BigDecimal.valueOf(price));
    }

    public static Product product(UUID id, String name, long price) {
        return new Product(id, name, BigDecimal.valueOf(price));
    }
}
