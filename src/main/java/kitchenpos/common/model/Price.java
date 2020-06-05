package kitchenpos.common.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import org.springframework.lang.NonNull;

@Embeddable
@Access(AccessType.FIELD)
public class Price implements Comparable<Price> {

    public static final Price ZERO = Price.of(BigDecimal.ZERO);
    private BigDecimal value;

    protected Price() {
    }

    private Price(@NonNull BigDecimal value) {
        if (Objects.isNull(value) ||
            value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("invalid price");
        }

        this.value = value;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public Price multiply(BigDecimal val) {
        return Price.of(this.value.multiply(val));
    }

    public Price add(Price price) {
        return Price.of(this.value.add(price.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Price o) {
        return this.value.compareTo(o.value);
    }
}
