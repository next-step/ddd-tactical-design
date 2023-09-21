package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class MenuProduct {
    private UUID productId;
    private Quantity quantity;
    private Price price;

    public MenuProduct(UUID productId, Quantity quantity, Price productPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = productPrice.multiplyQuantity(quantity);
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}
