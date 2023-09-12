package kitchenpos.products.tobe;

import kitchenpos.products.tobe.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class Fixtures {
    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(name, new FakePurgomalumClient(), BigDecimal.valueOf(price));
    }
}
