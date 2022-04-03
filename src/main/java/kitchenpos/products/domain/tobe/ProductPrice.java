package kitchenpos.products.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public ProductPrice() {
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

    public boolean isLessThan(BigDecimal value) {
        return price.compareTo(value) < 0;
    }

    public boolean isMoreThan(BigDecimal value) {
        return price.compareTo(value) > 0;
    }

    public boolean isSame(BigDecimal value) {
        return price.compareTo(value) == 0;
    }
}
