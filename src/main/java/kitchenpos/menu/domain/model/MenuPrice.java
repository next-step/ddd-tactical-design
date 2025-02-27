package kitchenpos.menu.domain.model;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_PRICE_CREATION_EXCEPTION;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private final BigDecimal value;

    public MenuPrice(BigDecimal value) {
        validatePrice(value);
        this.value = value;
    }

    protected MenuPrice() {
        this.value = null;
    }

    private void validatePrice(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(MENU_PRICE_CREATION_EXCEPTION.getMessage());
        }
    }

    @JsonValue
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
