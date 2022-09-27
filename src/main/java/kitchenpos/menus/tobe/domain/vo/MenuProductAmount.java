package kitchenpos.menus.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.global.vo.ValueObject;

public class MenuProductAmount extends ValueObject {

    private final BigDecimal value;

    public MenuProductAmount(BigDecimal productPrice, MenuProductQuantity quantity) {
        validate(productPrice);
        value = productPrice.multiply(BigDecimal.valueOf(quantity.getValue()));
    }

    private void validate(BigDecimal productPrice) {
        if (Objects.isNull(productPrice)) {
            throw new IllegalArgumentException();
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
        MenuProductAmount that = (MenuProductAmount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
