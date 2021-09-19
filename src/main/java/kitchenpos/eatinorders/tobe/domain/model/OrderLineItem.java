package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItem {

    private final UUID id;

    private final UUID menuId;

    private final Price price;

    private final long quantity;

    public OrderLineItem(final UUID id, final UUID menuId, final Price price, final long quantity) {
        this.id = id;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(quantity).multiply(price.value());
    }

    public UUID getId() {
        return id;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public long getQuantity() {
        return quantity;
    }
}
