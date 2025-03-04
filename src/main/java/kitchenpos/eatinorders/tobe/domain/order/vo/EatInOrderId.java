package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderId {
    private final UUID value;

    public EatInOrderId() {
        this(UUID.randomUUID());
    }

    public EatInOrderId(final String value) {
        this(UUID.fromString(value));
    }

    public EatInOrderId(final UUID value) {
        if(Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrderId id = (EatInOrderId) o;
        return Objects.equals(getValue(), id.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
