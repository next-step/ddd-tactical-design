package kitchenpos.menus.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuProductAmount {

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
}
