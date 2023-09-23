package kitchenpos.orders.eatinorders.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Embeddable
public class EatInOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", nullable = false)
    private List<EatInOrderLineItem> eatInOrderLineItems = new ArrayList<>();

    protected EatInOrderLineItems() {

    }

    public EatInOrderLineItems(List<EatInOrderLineItem> values) {
        this.eatInOrderLineItems = values;
    }

    public List<EatInOrderLineItem> getEatInOrderLineItems() {
        return Collections.unmodifiableList(eatInOrderLineItems);
    }
}
