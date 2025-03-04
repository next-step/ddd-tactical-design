package kitchenpos.order.common.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.exception.OrderLineItemQtyException;

@Embeddable
public record OrderLineItemQty(long quantity) {

    public static OrderLineItemQty of(long quantity, OrderType type) {
        if (type.equals(OrderType.TAKEOUT) || type.equals(OrderType.DELIVERY)) {
            if (quantity < 0) {
                throw new OrderLineItemQtyException(ErrorCode.ORDER_LINE_ITEM_QTY_NOT_ALLOWED.toString());
            }
        }
        return new OrderLineItemQty(quantity);
    }

    public long get() {
        return quantity;
    }
}

