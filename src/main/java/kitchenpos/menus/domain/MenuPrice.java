package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menus.exception.InvalidMenuPriceException;

@Embeddable
public class MenuPrice {

    @Column(name = "price", nullable = false, columnDefinition = "decimal(19, 2)")
    private BigDecimal value;

    protected MenuPrice() {
    }

    public MenuPrice(BigDecimal value) {
        this.value = value;
        validateNull(this.value);
        validateNegative(this.value);
    }

    private void validateNull(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new InvalidMenuPriceException("올바르지 않은 메뉴 가격입니다.");
        }
    }

    private void validateNegative(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMenuPriceException("메뉴 가격은 0보다 작을 수 없습니다.");
        }
    }

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
        return Objects.hash(value);
    }
}
