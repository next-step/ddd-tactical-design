package kitchenpos.menus.tobe.domain;

public class MenuProduct {
    private final Long productId;
    private final int price;
    private final int quantity;

    public MenuProduct(Long productId, int price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public int amount() {
        return price * quantity;
    }
}
