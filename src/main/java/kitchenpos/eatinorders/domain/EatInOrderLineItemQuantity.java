package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.ValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class EatInOrderLineItemQuantity extends ValueObject {
    private long value;

    public EatInOrderLineItemQuantity(long value) {
        this.value = value;
    }

    protected EatInOrderLineItemQuantity() {

    }

    public long getValue() {
        return value;
    }
}
