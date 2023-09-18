package kitchenpos.eatinorders.application.response;

import kitchenpos.eatinorders.domain.tobe.EatInOrderLineItem;

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
