package kitchenpos.global;

import java.math.BigDecimal;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

public final class TobeFixtures {

    private TobeFixtures() {
    }

    public static Name name(String value) {
        return new Name(value, new FakePurgomalumClient());
    }

    public static Price price(BigDecimal value) {
        return new Price(value);
    }

    public static Quantity quantity(long value) {
        return new Quantity(value);
    }

    public static Product product(String name) {
        return product(name, BigDecimal.ZERO);
    }

    public static Product product(String name, BigDecimal price) {
        return new Product(name(name), price(price));
    }
}
