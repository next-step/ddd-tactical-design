package kitchenpos.common.tobe.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Price {
    private BigDecimal price;

    private Price() {
    }

    public Price(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int compareTo(BigDecimal sum) {
        return price.compareTo(sum);
    }

    public BigDecimal multiply(long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price.setScale(0), price1.price.setScale(0));
    }

    @Override
    public int hashCode() {
        return Objects.hash(price.setScale(0));
    }
}
