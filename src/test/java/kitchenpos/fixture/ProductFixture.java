package kitchenpos.fixture;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import kitchenpos.support.domain.ProductPrice;

public class ProductFixture {

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(ProductName.from(name), ProductPrice.from(price));
    }

    public static Product product(final String name, final long price, ProfanityChecker profanityChecker) {
        return Product.of(ProductName.from(name, profanityChecker), ProductPrice.from(price));
    }
}
