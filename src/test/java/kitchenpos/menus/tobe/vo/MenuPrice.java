package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuPricePeriodException;

public class MenuPrice {
    private static final int MINIMUM_VALUE = 0;

    private final long value;

    public MenuPrice(final long value) {
        if (value < MINIMUM_VALUE) {
            throw new InvalidMenuPricePeriodException();
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
