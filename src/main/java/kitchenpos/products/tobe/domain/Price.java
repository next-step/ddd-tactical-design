package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    public Price(Long price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException();
        }
        var tempPrice = BigDecimal.valueOf(price);
        validate(tempPrice);
        this.price = tempPrice;
    }

    public Price(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    public void validate(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        return price.multiply(bigDecimal);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
        return Objects.hashCode(price);
    }
}
