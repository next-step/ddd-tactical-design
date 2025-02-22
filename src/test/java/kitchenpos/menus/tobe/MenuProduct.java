package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.MenuProductPrice;
import kitchenpos.menus.tobe.vo.MenuProductQuantity;

import java.util.Objects;

public class MenuProduct {
    private final Long seq;
    private final MenuProductPrice price;
    private final MenuProductQuantity quantity;
    private final long productId;

    public MenuProduct(final Long seq, final long price, final long quantity, final long productId) {
        this(seq, new MenuProductPrice(price), new MenuProductQuantity(quantity), productId);
    }

    public MenuProduct(final Long seq, final MenuProductPrice price, final MenuProductQuantity quantity, final long productId) {
        this.seq = seq;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    public boolean isSame(final long productId) {
        return this.seq == productId;
    }
    
    public long amount() {
        return Math.multiplyExact(price.getValue(), quantity.getValue());
    }

    public MenuProduct changePrice(final long changedPrice) {
        return new MenuProduct(seq, new MenuProductPrice(changedPrice), quantity, productId);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq);
    }
}
