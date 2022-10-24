package kitchenpos.menu.tobe.domain.vo;

import java.util.UUID;
import kitchenpos.common.price.Price;

public class MenuProduct {

    public final UUID productId;

    public final Price pricePerUnit;

    public final MenuProductQuantity quantity;

    public MenuProduct(
        final UUID productId,
        final Price pricePerUnit,
        final MenuProductQuantity quantity
    ) {
        this.productId = productId;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    public Price subtotal() {
        return this.pricePerUnit.multiply(this.quantity.value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuProduct that = (MenuProduct) o;

        if (!productId.equals(that.productId)) {
            return false;
        }
        if (!pricePerUnit.equals(that.pricePerUnit)) {
            return false;
        }
        return quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + pricePerUnit.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }
}
