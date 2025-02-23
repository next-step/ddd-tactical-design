package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.MenuId;
import kitchenpos.menus.tobe.vo.MenuProductPrice;
import kitchenpos.menus.tobe.vo.MenuProductQuantity;

import java.util.Objects;
import java.util.UUID;

public class MenuProduct {
    private Long seq;
    private MenuProductPrice price;
    private final MenuProductQuantity quantity;
    private MenuId menuId;
    private final long productId;

    public MenuProduct(final Long seq, final long price, final long quantity, final MenuId menuId, final long productId) {
        this(seq, new MenuProductPrice(price), new MenuProductQuantity(quantity), menuId, productId);
    }

    public MenuProduct(final Long seq, final MenuProductPrice price, final MenuProductQuantity quantity, final MenuId menuId, final long productId) {
        this.seq = seq;
        this.price = price;
        this.quantity = quantity;
        this.menuId = menuId;
        this.productId = productId;
    }

    public boolean isSameProduct(final long productId) {
        return this.productId == productId;
    }

    public long amount() {
        return Math.multiplyExact(price.getValue(), quantity.getValue());
    }

    public void changePrice(final long changedPrice) {
        this.price = new MenuProductPrice(changedPrice);
    }

    public long priceValue() {
        return price.getValue();
    }

    public long quantityValue() {
        return quantity.getValue();
    }

    public long productId() {
        return productId;
    }

    public UUID menuIdValue() {
        return menuId.getValue();
    }

    public void setMenuId(final MenuId menuId) {
        this.menuId = menuId;
    }

    public void setSeq(final long seq) {
        this.seq = seq;
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
