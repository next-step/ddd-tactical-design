package kitchenpos.orders.eatinorders.dto;

import kitchenpos.orders.eatinorders.domain.EatInOrderLineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EatInOrderLineItemResponse {
    private long seq;
    private UUID menuId;
    private long quantity;
    private BigDecimal price;

    public EatInOrderLineItemResponse() {
    }

    public EatInOrderLineItemResponse(long seq, UUID menuId, long quantity, BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static EatInOrderLineItemResponse fromEntity(EatInOrderLineItem orderLineItem) {
        return new EatInOrderLineItemResponse(
                orderLineItem.getSeqValue(),
                orderLineItem.getMenuIdValue(),
                orderLineItem.getQuantityValue(),
                orderLineItem.getPriceValue()
        );
    }

    public static List<EatInOrderLineItemResponse> fromEntities(List<EatInOrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                .map(EatInOrderLineItemResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
