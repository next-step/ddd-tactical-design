package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private BigDecimal price;

    protected Price() {
    }

    private Price(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Price plus(Price price) {
        return new Price(this.price.add(price.price));
    }

    public Price minus(Price price) {
        return new Price(this.price.subtract(price.price));
    }

    public static Price valueOf(final BigDecimal price) {
        return new Price(price);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Price)) {
            return false;
        }

        Price other = (Price) object;
        return Objects.equals(price, other.price);
    }

    public int hashCode() {
        return Objects.hashCode(price);
    }
}
