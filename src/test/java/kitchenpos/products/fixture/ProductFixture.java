package kitchenpos.products.fixture;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.profanity.ProfanityClient;

import java.math.BigDecimal;

public class ProductFixture {
    private static final ProfanityClient profanityclient = text -> false;

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(name, BigDecimal.valueOf(price));
    }


}
