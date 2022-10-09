package kitchenpos.eatinorders.domain.tobe;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderId {
    private final UUID value;

    public EatInOrderId(final UUID id) {
        this.value = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EatInOrderId that = (EatInOrderId) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
