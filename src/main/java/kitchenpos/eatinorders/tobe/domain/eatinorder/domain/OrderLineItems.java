package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
public class OrderLineItems {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_line_item", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = new ArrayList<>(orderLineItems);
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public List<Long> getMenuIds() {
        return orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItems that = (OrderLineItems) o;
        return (orderLineItems.containsAll(that.orderLineItems) && that.orderLineItems.containsAll(orderLineItems));
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderLineItems);
    }
}
