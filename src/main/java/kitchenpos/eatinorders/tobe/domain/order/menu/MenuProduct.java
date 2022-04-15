package kitchenpos.eatinorders.tobe.domain.order.menu;

import java.util.Objects;
import kitchenpos.common.domain.Money;
import kitchenpos.menus.tobe.domain.menu.MenuProductId;
import kitchenpos.products.tobe.domain.ProductId;

public final class MenuProduct {

    private final MenuProductId id;
    private final ProductId productId;
    private final Money productPrice;
    private final long quantity;

    public MenuProduct(
        MenuProductId id,
        ProductId productId,
        Money productPrice,
        long quantity
    ) {
        this.id = id;
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProduct)) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return quantity == that.quantity
            && Objects.equals(id, that.id)
            && Objects.equals(productId, that.productId)
            && Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, productPrice, quantity);
    }

    @Override
    public String toString() {
        return "MenuProduct{" +
            "id=" + id +
            ", productId=" + productId +
            ", productPrice=" + productPrice +
            ", quantity=" + quantity +
            '}';
    }
}
