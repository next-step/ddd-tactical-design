package kitchenpos.products.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    public Price() {
        // Needed by Hibernate
    }

    private Price(BigDecimal price) {
        this.price = price;
    }

    public static Price of(BigDecimal price) throws RuntimeException {
        if (!isPositiveValue(price)) {
            throw new NumberFormatException();
        }
        return new Price(price);
    }

    public static boolean isPositiveValue(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return true;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
