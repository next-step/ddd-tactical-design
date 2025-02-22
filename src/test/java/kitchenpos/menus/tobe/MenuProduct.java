package kitchenpos.menus.tobe;

public class MenuProduct {
    private final Long seq;
    private final long price;
    private final long quantity;
    private final long productId;

    public MenuProduct(final Long seq, final long price, final long quantity, final long productId) {
        this.seq = seq;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }
}
