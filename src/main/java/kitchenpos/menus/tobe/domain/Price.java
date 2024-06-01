package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {}

    public Price(BigDecimal price, long quantity) {
        checkValidatedPrice(price);
        BigDecimal result = price.multiply(BigDecimal.valueOf(quantity));
        this.price = result;
    }

    public Price(BigDecimal price) {
        checkValidatedPrice(price);
        this.price = price;
    }

    private void checkValidatedPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public Price add(Price inputPrice) {
        this.price = this.price.add(inputPrice.price);
        return new Price(price);
    }

    public boolean isLessThen(Price inputPrice) {
        return price.compareTo(inputPrice.price) < 0;
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
}
