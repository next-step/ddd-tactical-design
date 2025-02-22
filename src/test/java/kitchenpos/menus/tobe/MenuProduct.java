package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.Price;
import kitchenpos.menus.tobe.vo.Quantity;

public class MenuProduct {
    private final Long seq;
    private final Price price;
    private final Quantity quantity;
    private final long productId;

    public MenuProduct(final Long seq, final long price, final long quantity, final long productId) {
        this(seq, new Price(price), new Quantity(quantity), productId);
    }

    public MenuProduct(final Long seq, final Price price, final Quantity quantity, final long productId) {
        this.seq = seq;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }
}
