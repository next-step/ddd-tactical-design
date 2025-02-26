package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.OrderTableId;

import java.util.List;
import java.util.Objects;

public class EatInOrder {
    private final EatInOrderLineItems eatInOrderLineItems;
    private final OrderTableId orderTableId;

    public EatInOrder(final List<EatInOrderLineItem> eatInOrderLineItems, final OrderTableId orderTableId) {
        if (Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException();
        }
        this.eatInOrderLineItems = new EatInOrderLineItems(eatInOrderLineItems);
        this.orderTableId = orderTableId;
    }
}
