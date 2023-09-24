package kitchenpos.orders.eatinorders.domain;

import kitchenpos.common.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class EatInOrderLineItemQuantity extends ValueObject {

    @Column(name = "quantity")
    private Long value;

    public EatInOrderLineItemQuantity(Long value) {
        this.value = value;
    }

    protected EatInOrderLineItemQuantity() {

    }

    public long getValue() {
        return value;
    }
}
