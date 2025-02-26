package kitchenpos.eatinorders.tobe.domain.vo;

import java.util.Objects;
import java.util.UUID;

public class OrderTableId {
    private final UUID value;

    public OrderTableId() {
        this(UUID.randomUUID());
    }

    public OrderTableId(final UUID value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderTableId that = (OrderTableId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
