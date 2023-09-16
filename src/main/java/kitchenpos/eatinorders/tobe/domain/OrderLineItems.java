package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class OrderLineItems {

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    protected OrderLineItems() {
    }

    public List<OrderLineItem> getOrderLineItems() {
        return Collections.unmodifiableList(this.orderLineItems);
    }
}
