package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class VerifiedPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public VerifiedPrice(BigDecimal price) {
        checkPrice(price);
        this.price = price;
    }

    public VerifiedPrice() {

    }

    private void checkPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
