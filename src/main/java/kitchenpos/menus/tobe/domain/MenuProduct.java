package kitchenpos.menus.tobe.domain;

import java.util.UUID;

public class MenuProduct {

    private UUID productId;

    private Price price;

    private Quantity quantity;

    public MenuProduct(UUID productId, Price price, Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Price amount() {
        return price.times(quantity.quantity());
    }

}
