package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;

@Embeddable
public record MenuProductQty(long quantity) {

    public static MenuProductQty of(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ErrorCode.MENU_PRODUCT_QTY_NOT_ALLOWED.toString());
        }
        return new MenuProductQty(quantity);
    }

    public boolean isNegative() {
        return this.quantity < 0;
    }
}

