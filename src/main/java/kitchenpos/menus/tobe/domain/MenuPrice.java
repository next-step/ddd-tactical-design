package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    public MenuPrice() {

    }

    public MenuPrice(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public int compareTo(BigDecimal x) {
        return this.value.compareTo(x);
    }
}
