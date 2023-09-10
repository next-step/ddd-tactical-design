package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Price {

    private BigDecimal price;

    public Price() {
    }

    public Price(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        return price.multiply(bigDecimal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price1 = (Price) o;

        return price.equals(price1.price);
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
