package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menus.exception.InvalidMenuProductPriceException;

@Embeddable
public class MenuProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected MenuProductPrice() {
    }

    public MenuProductPrice(BigDecimal value) {
        this.value = value;
        validateNull(this.value);
        validateNegative(this.value);
    }

    private void validateNull(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new InvalidMenuProductPriceException("올바르지 않은 메뉴의 상품 가격입니다.");
        }
    }

    private void validateNegative(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMenuProductPriceException("메뉴의 상품 가격은 0보다 작을 수 없습니다.");
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
        MenuProductPrice that = (MenuProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
