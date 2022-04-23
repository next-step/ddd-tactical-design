package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {

    private final UUID productId;
    private final BigDecimal price;
    private final int quantity;

    public MenuProduct(final UUID productId, final long price, final int quantity) {
        this.productId = productId;
        this.price = BigDecimal.valueOf(price);
        this.quantity = quantity;
    }

    public int amount() {
        return price.intValue() * quantity;
    }
}
