package kitchenpos.eatinorders.ui.response;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;

public class EatInOrderLineItemResponse {

    private final Long seq;
    private final UUID menuId;
    private final long quantity;
    private final BigDecimal price;

    public EatInOrderLineItemResponse(Long seq, UUID menuId, long quantity, BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static List<EatInOrderLineItemResponse> of(List<EatInOrderLineItem> eatInOrderLineItems) {
        return eatInOrderLineItems.stream()
            .map(EatInOrderLineItemResponse::from)
            .collect(toList());
    }

    private static EatInOrderLineItemResponse from(EatInOrderLineItem eatInOrderLineItem) {
        return new EatInOrderLineItemResponse(
            eatInOrderLineItem.getSeq(),
            eatInOrderLineItem.getMenuId(),
            eatInOrderLineItem.getQuantity(),
            eatInOrderLineItem.getPriceValue()
        );
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
