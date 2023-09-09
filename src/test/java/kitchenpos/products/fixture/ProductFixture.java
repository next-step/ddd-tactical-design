package kitchenpos.products.fixture;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.policy.ProfanityPolicy;

import java.math.BigDecimal;

public class ProductFixture {
    private static final ProfanityPolicy PROFANITY_POLICY = text -> false;

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(name, BigDecimal.valueOf(price), PROFANITY_POLICY);
    }


}
