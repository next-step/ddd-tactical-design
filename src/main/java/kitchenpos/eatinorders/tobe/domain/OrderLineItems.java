package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.aspectj.weaver.ast.Or;

@Embeddable
public class OrderLineItems {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    protected OrderLineItems() {
    }

    public boolean isEmpty() {
        return this.orderLineItems.isEmpty();
    }

    public void mapOrder(Order order) {
        for (OrderLineItem orderLineItem : orderLineItems) {
            orderLineItem.mapOrder(order);
        }
    }

    public List<OrderLineItem> getOrderLineItems() {
        return Collections.unmodifiableList(this.orderLineItems);
    }
}
