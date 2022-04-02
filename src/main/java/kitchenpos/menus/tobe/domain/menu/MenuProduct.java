package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;
import kitchenpos.common.domain.Money;
import kitchenpos.products.tobe.domain.ProductId;

public final class MenuProduct {

    private final MenuProductId seq;
    private final ProductId productId;
    private final Money price;
    private final long quantity;

    public MenuProduct(MenuProductId seq, ProductId productId, Money price, long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(String.format("수량은 0보다 작을 수 없습니다. quantity: %s", quantity));
        }
        this.seq = seq;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuProduct(ProductId productId, Money price, long quantity) {
        this(null, productId, price, quantity);
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
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return "MenuProduct{" +
            "seq=" + seq +
            ", productId=" + productId +
            ", quantity=" + quantity +
            '}';
    }
}
