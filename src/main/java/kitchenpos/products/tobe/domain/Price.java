package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    private BigDecimal price;

    protected Price() {
    }

    public Price(long price) {
        this(BigDecimal.valueOf(price));
    }

    public Price(BigDecimal price) {
        if(BigDecimal.ZERO.compareTo(price) > 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
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
        return price != null ? price.hashCode() : 0;
    }
}
