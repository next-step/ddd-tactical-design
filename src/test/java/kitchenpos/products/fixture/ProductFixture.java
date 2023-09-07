package kitchenpos.products.fixture;

import kitchenpos.profanity.ProfanityClient;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {
    private static final ProfanityClient profanityclient = text -> false;

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(name, profanityclient, BigDecimal.valueOf(price));
    }


}
