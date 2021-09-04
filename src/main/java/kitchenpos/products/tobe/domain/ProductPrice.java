package kitchenpos.products.tobe.domain;

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

    public ProductPrice(final BigDecimal price) {
        checkPrice(price);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void checkPrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal multiply(final Long quantity) {
        return price.multiply(new BigDecimal(quantity));
    }
}
