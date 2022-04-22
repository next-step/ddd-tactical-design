package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.eatinorders.tobe.exception.InvalidStockQuantityException;

@Embeddable
public class Stock {

    private static final int MINIMUM_QUANTITY = 1;

    @Column(name = "quantity", nullable = false)
    private long value;

    protected Stock() { }

    public Stock(long value) {
        this.value = value;

        if (value < MINIMUM_QUANTITY) {
            throw new InvalidStockQuantityException();
        }
    }

    public long value() {
        return value;
    }
}
