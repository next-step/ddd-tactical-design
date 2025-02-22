package kitchenpos.menus.tobe.vo;

import kitchenpos.menus.tobe.exception.InvalidMenuProductPricePeriodException;

import java.util.Objects;

public class MenuProductPrice {
    private static final int MINIMUM_VALUE = 0;

    private final long value;

    public MenuProductPrice(final long value) {
        if (value < MINIMUM_VALUE) {
            throw new InvalidMenuProductPricePeriodException();
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuProductPrice that = (MenuProductPrice) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
