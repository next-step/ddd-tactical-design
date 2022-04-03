package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;
import kitchenpos.common.domain.Money;
import kitchenpos.products.tobe.domain.ProductId;

public final class MenuProduct {

    private final MenuProductId seq;
    private final ProductId productId;
    private final Money productPrice;
    private final long quantity;

    public MenuProduct(MenuProductId seq, ProductId productId, Money productPrice, long quantity) {
        validQuantity(quantity);
        validProduct(productId);
        this.seq = seq;
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    private void validProduct(ProductId productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("상품 id는 비어 있을 수 없습니다. productId: null");
        }
    }

    private void validQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                String.format("수량은 0보다 작을 수 없습니다. quantity: %s", quantity)
            );
        }
    }

    public MenuProduct(ProductId productId, Money price, long quantity) {
        this(null, productId, price, quantity);
    }

    public MenuProduct(ProductId productId, long price, long quantity) {
        this(null, productId, new Money(price), quantity);
    }

    public Money calculatePrice() {
        return productPrice.times(quantity);
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
            ", productPrice=" + productPrice +
            ", quantity=" + quantity +
            '}';
    }
}
