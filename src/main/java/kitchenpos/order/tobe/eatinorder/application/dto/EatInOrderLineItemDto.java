package kitchenpos.order.tobe.eatinorder.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem;

public class EatInOrderLineItemDto {

    private final UUID eatInOrderMenuId;
    private final BigDecimal price;
    private final long quantity;

    public EatInOrderLineItemDto(UUID eatInOrderMenuId, BigDecimal price, long quantity) {
        this.eatInOrderMenuId = eatInOrderMenuId;
        this.price = price;
        this.quantity = quantity;
    }

    public static EatInOrderLineItemDto of(EatInOrderLineItem entity) {
        return new EatInOrderLineItemDto(entity.getEatInOrderMenuId(), entity.getPrice(), entity.getQuantity());
    }

    public UUID getEatInOrderMenuId() {
        return eatInOrderMenuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
