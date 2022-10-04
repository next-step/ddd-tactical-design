package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice(final BigDecimal price) {
        checkPrice(price);
        this.price = price;
    }

    protected ProductPrice() {

    }

    private void checkPrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal multiply(final long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getPrice() {
        return price;
    }
}
