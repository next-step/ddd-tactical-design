package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private BigDecimal value;

    private ProductPrice(final int price) {
        validate(price);
        this.value = BigDecimal.valueOf(price);
    }

    public static ProductPrice valueOf(final int price) {
        return new ProductPrice(price);
    }

    public BigDecimal checkProductPriceValue() {
        return value;
    }

    private void validate(final int price) {
        if (price < 0) throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
