package kitchenpos.eatinorders.order.tobe.domain.vo;

import java.util.Objects;
import java.util.UUID;

public class OrderLineItem {

    private final UUID menuId;
    private final Long price;
    private final int quantity;

    public OrderLineItem(UUID menuId, Long price, int quantity) {
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItem that = (OrderLineItem) o;
        return quantity == that.quantity && Objects.equals(menuId, that.menuId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, price, quantity);
    }
}
