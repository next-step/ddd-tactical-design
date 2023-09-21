package kitchenpos.common.domain;

import kitchenpos.menus.domain.tobe.Quantity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    public Price(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(String.valueOf(price));
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public Price multiply(Quantity quantity) {
        return multiply(new BigDecimal(quantity.getValue()));
    }

    private Price multiply(BigDecimal value) {
        return new Price(this.getValue().multiply(value));
    }

    public Boolean isGreaterThan(Price price) {
        return this.value.compareTo(price.getValue()) > 0;
    }
}
