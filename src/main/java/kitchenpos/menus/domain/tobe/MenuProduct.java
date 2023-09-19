package kitchenpos.menus.domain.tobe;

import java.util.UUID;

public class MenuProduct {
    private final UUID productId;
    private final int price;
    private final int quantity;

    public MenuProduct(UUID productId, int price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }


    public int amount() {
        return this.price * this.quantity;
    }
}
