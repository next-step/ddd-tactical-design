package kitchenpos.eatinorders.domain.tobe;

import java.util.Objects;
import java.util.UUID;

public class OrderTableId {
    private UUID value;

    public OrderTableId(final UUID id) {
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

        OrderTableId that = (OrderTableId) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
