package kitchenpos.common.domain;

import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    protected Price(BigDecimal price) {
        if(ObjectUtils.isEmpty(price) || isNegative(price)) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    private boolean isNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getValue() {
        return this.price;
    }
}
