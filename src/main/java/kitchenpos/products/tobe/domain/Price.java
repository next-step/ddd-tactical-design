package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.WrongPriceException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    static final int ZERO = 0;

    protected Price() {
    }

    public Price(final BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        final Price price1 = (Price) o;
        return getPrice().equals(price1.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }

    private void validatePrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new WrongPriceException();
        }
    }
}
