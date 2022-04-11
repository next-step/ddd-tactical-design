package kitchenpos.common.domain;

import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    public static final Price ZERO = Price.of(BigDecimal.ZERO);

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    protected Price(BigDecimal price) {
        if(ObjectUtils.isEmpty(price) || isNegative(price)) {
            throw new IllegalArgumentException("price is invalid");
        }
        this.price = price;
    }

    private boolean isNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getValue() {
        return this.price;
    }

    public Price add(final Price price) {
        return new Price(this.price.add(price.getValue()));
    }

    public Price multiply(final Quantity quantity) {
        return new Price(this.price.multiply(BigDecimal.valueOf(quantity.getValue())));
    }

    public boolean isLessThanOrEquals(Price price) {
        return this.price.compareTo(price.getValue()) <= 0;
    }

    public boolean isLessThan(Price price) {
        return this.price.compareTo(price.getValue()) < 0;
    }

    public boolean isGreaterThanOrEquals(Price price) {
        return this.price.compareTo(price.getValue()) >= 0;
    }

    public boolean isGreaterThan(Price price) {
        return this.price.compareTo(price.getValue()) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Price p = (Price) o;
        return price.equals(p.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
