package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class OrderLineItems {

    @OneToMany
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems(){

    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
