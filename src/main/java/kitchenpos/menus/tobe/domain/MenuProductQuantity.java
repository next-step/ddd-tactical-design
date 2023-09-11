package kitchenpos.menus.tobe.domain;

public class MenuProductQuantity {

    private final long quantity;

    protected MenuProductQuantity() {
        this.quantity = 0;
    }

    public MenuProductQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getValue() {
        return quantity;
    }

}
