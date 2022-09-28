package kitchenpos.products.tobe;

import java.math.BigDecimal;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

public final class ProductFixtures {

    private ProductFixtures() {
    }

    public static Product product(String name, long price) {
        return new Product(name(name), price(price));
    }

    public static Price price(long value) {
        return new Price(BigDecimal.valueOf(value));
    }

    public static Name name(String value) {
        return new Name(value, new FakePurgomalumClient());
    }
}
