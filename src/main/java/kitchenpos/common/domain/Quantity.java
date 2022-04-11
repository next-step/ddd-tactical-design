package kitchenpos.common.domain;

import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    public static final Quantity ZERO = Quantity.of(0);

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected Quantity() {
    }

    protected Quantity(final long quantity) {
        if(ObjectUtils.isEmpty(quantity) || isNegative(quantity)) {
            throw new IllegalArgumentException("quantity is invalid");
        }
        this.quantity = quantity;
    }

    private boolean isNegative(long quantity) {
        return quantity < 0L;
    }

    public static Quantity of(final long quantity) {
        return new Quantity(quantity);
    }

    public long getValue() {
        return this.quantity;
    }
}
