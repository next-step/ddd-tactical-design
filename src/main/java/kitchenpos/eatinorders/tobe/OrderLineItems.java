package kitchenpos.eatinorders.tobe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class OrderLineItems {

    @Embedded
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        validateDuplicateMenuId(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    private void validateDuplicateMenuId(List<OrderLineItem> orderLineItems) {
        long count = orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .distinct()
                .count();

        if (orderLineItems.size() != count) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal totalPrice() {
        return orderLineItems.stream()
                .map(orderLineItem -> orderLineItem.price())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
