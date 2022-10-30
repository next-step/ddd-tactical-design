package kitchenpos.products.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {
    }

    public ProductPrice(Long price) {
        this.price = validatePositive(price);
    }

    void change(Long amount) {
        this.price = validatePositive(amount);
    }

    private BigDecimal validatePositive(Long price) {
        if (price == null || BigDecimal.valueOf(price).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        return BigDecimal.valueOf(price);
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
