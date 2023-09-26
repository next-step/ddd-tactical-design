package kitchenpos.eatinorders.order.domain.vo;

import java.util.Objects;
import java.util.UUID;

public class OrderLineItemVo {
    private UUID menuId;
    private Long price;
    private long quantity;

    public OrderLineItemVo() {
    }

    public OrderLineItemVo(UUID menuId, Long price, long quantity) {
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

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItemVo that = (OrderLineItemVo) o;
        return quantity == that.quantity && Objects.equals(menuId, that.menuId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, price, quantity);
    }
}
