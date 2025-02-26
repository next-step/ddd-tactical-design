package kitchenpos.products.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.domain.exception.InvalidProductException;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {
    }

    public ProductPrice(final BigDecimal price) {
        this.price = checkProductPrice(price);
    }

    private BigDecimal checkProductPrice(final BigDecimal price) {
        if (price == null) {
            throw new InvalidProductException("상품의 가격은 존재해야 한다.");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductException("상품의 가격은 0보다 커야 한다.");
        }
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
        return Objects.hash(price);
    }

    public BigDecimal getPrice() {
        return price;
    }
}
