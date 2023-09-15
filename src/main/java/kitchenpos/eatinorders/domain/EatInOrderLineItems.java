package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;


@Embeddable
public class EatInOrderLineItems {

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<EatInOrderLineItem> values;

    public EatInOrderLineItems(List<EatInOrderLineItem> values) {
        if(values == null || values.isEmpty()){
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_LINE_ITEMS_IS_EMPTY);
        }
        this.values = values;
    }

    protected EatInOrderLineItems() {

    }

    public List<EatInOrderLineItem> getValues() {
        return Collections.unmodifiableList(values);
    }
}
