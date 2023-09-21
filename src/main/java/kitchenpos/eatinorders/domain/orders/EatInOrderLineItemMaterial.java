package kitchenpos.eatinorders.domain.orders;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderLineItemMaterial {
    private final UUID menuId;
    private final long quantity;

    public EatInOrderLineItemMaterial(UUID menuId, long quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemMaterial that = (EatInOrderLineItemMaterial) o;
        return quantity == that.quantity && Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, quantity);
    }
}
