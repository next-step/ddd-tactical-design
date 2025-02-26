package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuPricePeriodException;

import java.util.Objects;

public class MenuPrice {

    private static final int MINIMUM_VALUE = 0;

    private final long value;

    public MenuPrice(final long value) {
        if (value < MINIMUM_VALUE) {
            throw new InvalidMenuPricePeriodException();
        }
        this.value = value;
    }

    public boolean isGreaterThan(final long price) {
        return this.value > price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuPrice that = (MenuPrice) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
