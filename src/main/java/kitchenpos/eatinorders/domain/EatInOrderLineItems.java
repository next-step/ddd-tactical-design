package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;

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

    public EatInOrderLineItems(List<EatInOrderLineItem> values) {
        if (values == null || values.isEmpty()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_LINE_ITEMS_IS_EMPTY);
        }
        this.eatInOrderLineItems = values;
    }

    protected EatInOrderLineItems() {

    }

    public List<EatInOrderLineItem> getEatInOrderLineItems() {
        return Collections.unmodifiableList(eatInOrderLineItems);
    }
}
