package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItem {

    public OrderLineItem(final UUID id, final UUID menuId, final Price price, final long quantity) {
    }

    public BigDecimal getAmount() {
        return BigDecimal.ZERO;
    }

    public UUID getId() {
        return null;
    }

    public UUID getMenuId() {
        return null;
    }

    public BigDecimal getPrice() {
        return null;
    }

    public long getQuantity() {
        return 0;
    }
}
