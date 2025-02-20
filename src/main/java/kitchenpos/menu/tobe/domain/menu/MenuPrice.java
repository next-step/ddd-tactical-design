package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.common.exception.ErrorCode;
import kitchenpos.common.exception.MenuException;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private Long value;

    public static MenuPrice of(Long value) {
        return new MenuPrice(value);
    }

    private MenuPrice(Long value) {
        validate(value);
        this.value = value;
    }
    protected MenuPrice() {}
    private void validate(Long value) {
        if (Objects.isNull(value) || value.compareTo(0L) <= 0 ) { // 맘에안드네 곧 변경예정
            throw new MenuException(ErrorCode.MENU_PRICE_INVALID);
        }
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
