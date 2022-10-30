package kitchenpos.common.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    @Column()
    private BigDecimal price;

    public Price(BigDecimal price) {
        if (Objects.isNull(price) || MIN_PRICE.compareTo(price) > 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    protected Price() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
