package kitchenpos.products.domain.tobe;

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

    public ProductPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public static ProductPrice valueOf(int value) {
        return new ProductPrice(BigDecimal.valueOf(value));
    }

    public static ProductPrice valueOf(BigDecimal value) {
        return new ProductPrice(value);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductPrice multiply(ProductPrice value) {
        return multiply(value.price);
    }

    public ProductPrice multiply(BigDecimal value) {
        return new ProductPrice(price.multiply(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPrice)) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }
}
