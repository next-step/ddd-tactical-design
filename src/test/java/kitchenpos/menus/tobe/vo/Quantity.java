package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuProductQuantityException;

public class Quantity {
    private static final int MINIMUM_VALUE = 0;

    private final long value;

    public Quantity(final long value) {
        if (value < MINIMUM_VALUE) {
            throw new InvalidMenuProductQuantityException();
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
