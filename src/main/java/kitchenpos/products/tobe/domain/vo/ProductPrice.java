package kitchenpos.products.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public ProductPrice(BigDecimal price) {
        if (Objects.isNull(price) || MIN_PRICE.compareTo(price) > 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    protected ProductPrice() {}

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
