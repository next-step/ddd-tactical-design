package kitchenpos.eatinorders.order.tobe.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class EatInOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "eatInOrder")
    private List<EatInOrderLineItem> values = new ArrayList<>();

    protected EatInOrderLineItems() {
    }

    private EatInOrderLineItems(final List<EatInOrderLineItem> values) {
        this.values = values;
    }

    static EatInOrderLineItems of(final EatInOrderLineItem... eatInOrderLineItems) {
        return of(List.of(eatInOrderLineItems));
    }

    static EatInOrderLineItems of(final List<EatInOrderLineItem> eatInOrderLineItems) {
        if (eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문상품은 1개 이상이여야 합니다.");
        }
        return new EatInOrderLineItems(eatInOrderLineItems);
    }

    void makeRelations(final EatInOrder eatInOrder) {
        for (final EatInOrderLineItem value : values) {
            value.makeRelation(eatInOrder);
        }
    }

    public List<EatInOrderLineItem> values() {
        return Collections.unmodifiableList(values);
    }
}
