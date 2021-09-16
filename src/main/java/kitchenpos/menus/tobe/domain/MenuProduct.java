package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.WrongPriceException;

import java.math.BigDecimal;

public class MenuProduct {
    private final Long seq;

    private final BigDecimal productId;

    private final long quantity;

    private final Price price;

    public MenuProduct(final Long seq, final BigDecimal productId, final long quantity, final Price price) {
        validateQuantity(quantity);
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    private void validateQuantity(final long quantity) {
        if (quantity < 0) {
            throw new WrongPriceException();
        }
    }
}
