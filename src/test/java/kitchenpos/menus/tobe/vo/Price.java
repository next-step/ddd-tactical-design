package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuProductPricePeriodException;

public class Price {
    private static final int MINIMUM_VALUE = 0;

    private final long value;

    public Price(final long value) {
        if (value < MINIMUM_VALUE) {
            throw new InvalidMenuProductPricePeriodException();
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
