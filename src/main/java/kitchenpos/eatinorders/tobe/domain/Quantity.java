package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    private static final int MINIMUM_QUANTITY = 1;

    @Column(name = "quantity", nullable = false)
    private long value;

    protected Quantity() { }

    public Quantity(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public boolean hasMoreThanOne() {
        return value >= MINIMUM_QUANTITY;
    }
}
