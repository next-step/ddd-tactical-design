package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {}

    private Price(BigDecimal price) {
        if (price == null || price.intValue() < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_MORE_ZERO);
        }
        this.price = price;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getPrice() {
        return price;
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
        return Objects.hash(price);
    }

    public boolean isGreaterThan(Price sum) {
        int result = this.price.compareTo(sum.price);
        return result > 0;
    }
}
