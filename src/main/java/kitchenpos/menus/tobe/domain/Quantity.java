package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    @Column(name = "quantity", nullable = false)
    private long value;

    protected Quantity() {
    }

    public Quantity(long value) {
        validate(value);
        this.value = value;
    }

    private void validate(long value) {
        if (value < 0) {
            throw new IllegalArgumentException(
                String.format("quantity 는 0보다 작을 수 없습니다. value: %s", this.value)
            );
        }
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
