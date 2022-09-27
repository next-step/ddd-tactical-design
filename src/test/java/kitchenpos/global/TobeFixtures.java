package kitchenpos.global;

import java.math.BigDecimal;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import kitchenpos.products.application.FakePurgomalumClient;

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
}
