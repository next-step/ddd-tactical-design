package kitchenpos.menus.domain.tobe;

import kitchenpos.common.domain.Price;

import java.util.UUID;

public class MenuProduct {
    private final UUID productId;
    private final Price price;
    private final Quantity quantity;

    public MenuProduct(final UUID productId, final Price price, final Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Price amount() {
        return this.price.multiply(quantity);
    }
}
