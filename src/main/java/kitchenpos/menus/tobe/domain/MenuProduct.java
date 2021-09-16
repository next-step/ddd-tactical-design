package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;

public class MenuProduct {
    private final Long seq;

    private final BigDecimal productId;

    private final long quantity;

    public MenuProduct(final Long seq, final BigDecimal productId, final long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }
}
