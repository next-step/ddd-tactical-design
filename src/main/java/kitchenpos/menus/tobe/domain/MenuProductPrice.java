package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Objects;

public class MenuProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    public MenuProductPrice() {

    }

    public MenuProductPrice(final BigDecimal value) {
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
}
