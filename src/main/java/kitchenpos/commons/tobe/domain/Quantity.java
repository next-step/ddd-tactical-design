package kitchenpos.commons.tobe.domain;

import java.util.Objects;

public class Quantity {

    private final long quantity;

    public Quantity(final long quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량 값이 필수고, 수량은 0 미만의 수량 값을 가질 수 없습니다");
        }
    }

    public long value() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Quantity quantity = (Quantity) o;
        return this.quantity == quantity.quantity;
    }

    @Override
    public int hashCode() {
        return (int) (quantity ^ (quantity >>> 32));
    }
}
