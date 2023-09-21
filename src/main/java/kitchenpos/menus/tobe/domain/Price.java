package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private final BigDecimal price;
    public Price(long price) {
        if (price < 0) {
            throw new IllegalArgumentException(String.format("가격은 0원 이상이어야 합니다. 현재 값: %s", price));
        }
        this.price = new BigDecimal(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
