package kitchenpos.eatinorders.ui.response;

import kitchenpos.eatinorders.domain.tobe.EatInOrderLineItem;
import kitchenpos.eatinorders.ui.request.EatInOrderLineItemRequest;

import java.math.BigDecimal;

public class EatInOrderLineItemResponse {

    private BigDecimal price;
    private Long quantity;

    public EatInOrderLineItemResponse(BigDecimal price, Long quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public static EatInOrderLineItemResponse of(EatInOrderLineItem eatInOrderLineItem) {
        return new EatInOrderLineItemResponse(eatInOrderLineItem.getPrice(), eatInOrderLineItem.getQuantity());
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }
}
