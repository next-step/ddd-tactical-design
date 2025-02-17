package kitchenpos.menu.domain.model;

import java.math.BigDecimal;

public interface MenuPriceValidator {
    void validate(final BigDecimal price);
}
