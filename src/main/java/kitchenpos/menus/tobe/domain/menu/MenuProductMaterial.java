package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;
import java.util.UUID;

public class MenuProductMaterial {
    private final UUID productId;
    private final long quantity;

    public MenuProductMaterial(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public long getQuantity() {
        return this.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductMaterial that = (MenuProductMaterial) o;
        return quantity == that.quantity && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}
