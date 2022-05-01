package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    public Price(BigDecimal price) {
        validation(price);
        this.price = price;
    }

    private void validation(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("가격은 필수입니다.");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이여야 합니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Price add(Price addPrice) {
        return new Price(this.price.add(addPrice.price));
    }

    public int compareTo(Price totalPrice) {
        return price.compareTo(totalPrice.price);
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
