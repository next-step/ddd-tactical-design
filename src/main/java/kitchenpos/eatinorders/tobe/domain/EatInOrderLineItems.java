package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableException;

/*
매장 내 식사 주문의 주문 항목
*/

@Embeddable
public class EatInOrderLineItems {

    private OrderLineItems orderLineItems;

    protected EatInOrderLineItems() {
    }

    public EatInOrderLineItems(final OrderLineItems orderLineItems) {
        this.orderLineItems = orderLineItems;
        validate();
    }

    private void validate() {
        if (hasNegativeQuantity()) {
            throw new InvalidOrderTableException("손님이 없는 주문 테이블은 주문을 받을 수 없습니다");
        }
    }

    private boolean hasNegativeQuantity() {
        return orderLineItems.getOrderLineItems().stream()
                .anyMatch(orderLineItem -> orderLineItem.quantity() < 0);
    }

    public OrderLineItems get() {
        return orderLineItems;
    }
}
