package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price")
    private BigDecimal value;

    protected ProductPrice() {
    }

    private ProductPrice(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public static ProductPrice valueOf(final BigDecimal price) {
        return new ProductPrice(price);
    }

    public BigDecimal checkProductPriceValue() {
        return value;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException();
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
