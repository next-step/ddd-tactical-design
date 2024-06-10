package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.Objects;

public class OrderLineItems {
    private final List<OrderLineItem> orderLineItems;

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public boolean isEmpty() {
        if(Objects.isNull(this.orderLineItems) || orderLineItems.isEmpty()){
            return true;
        }
        return false;
    }
}
