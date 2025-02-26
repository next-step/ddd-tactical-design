package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductQuantityException;

import java.util.Objects;

public class MenuProductQuantity {

    private static final int MINIMUM_VALUE = 0;

    private final long value;

    public MenuProductQuantity(final long value) {
        if (value < MINIMUM_VALUE) {
            throw new InvalidMenuProductQuantityException();
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
        final MenuProductQuantity that = (MenuProductQuantity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
