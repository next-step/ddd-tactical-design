package kitchenpos.core.shared.value;

import kitchenpos.core.shared.domain.ValueObject;

public class Quantity extends ValueObject<Quantity> {
    public static final Quantity ZERO = Quantity.of(0);

    private final Long value;

    private Quantity(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 음수가 될 수 없습니다.");
        }
        this.value = value;
    }

    public static Quantity of(long value) {
        return new Quantity(value);
    }

    public long getValue() {
        return value;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{value};
    }
    @Override
    public String toString() {
        return value + " 개";
    }
}
