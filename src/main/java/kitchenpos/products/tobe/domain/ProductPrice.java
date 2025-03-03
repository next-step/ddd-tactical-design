package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {
    }

    public ProductPrice(final BigDecimal price) {
        if (BigDecimal.ZERO.compareTo(price) > 0) throw new IllegalArgumentException("상품 가격은 0보다 작을 수 없습니다.");
        this.price = price;
    }

    public BigDecimal value() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice that)) return false;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
