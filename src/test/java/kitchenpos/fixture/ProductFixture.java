package kitchenpos.fixture;

import kitchenpos.products.tobe.domain.FakeProfanities;
import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {
    public static Product 상품() {
        return 상품(BigDecimal.valueOf(1000));
    }

    public static Product 상품(BigDecimal price) {
        return new Product(
                new Name("올바른 이름", new FakeProfanities()), new Price(price));
    }
}
