package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.Price;

public class MenuProduct {
    private final Long seq;
    private final Price price;
    private final long quantity;
    private final long productId;

    public MenuProduct(final Long seq, final long price, final long quantity, final long productId) {
        this(seq, new Price(price), quantity, productId);
    }

    public MenuProduct(final Long seq, final Price price, final long quantity, final long productId) {
        this.seq = seq;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }
}
