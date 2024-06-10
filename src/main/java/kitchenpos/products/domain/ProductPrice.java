package kitchenpos.products.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {}

    protected ProductPrice(final BigDecimal price) {
        this.setPrice(price);
    }

    private void setPrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("금액을 입력하지 않았거나 음수를 입력할 수 없습니다.");
        }

        this.price = price;
    }

    protected BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
