package kitchenpos.products.fixture;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {
    private static final PurgomalumClient profanityclient = text -> false;

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(name, profanityclient, BigDecimal.valueOf(price));
    }


}
