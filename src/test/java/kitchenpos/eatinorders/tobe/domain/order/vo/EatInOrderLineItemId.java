package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderLineItemId {
    private final UUID value;

    public EatInOrderLineItemId() {
        this(UUID.randomUUID());
    }

    public EatInOrderLineItemId(final String value) {
        this(UUID.fromString(value));
    }

    public EatInOrderLineItemId(final UUID value) {
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
        final EatInOrderLineItemId that = (EatInOrderLineItemId) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
