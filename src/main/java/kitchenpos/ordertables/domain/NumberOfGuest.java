package kitchenpos.ordertables.domain;

import kitchenpos.common.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuest extends ValueObject {

    @Column(name = "number_of_guests", nullable = false)
    private int values;

    protected NumberOfGuest() {

    }

    public NumberOfGuest(int values) {
        this.values = values;
    }

}
