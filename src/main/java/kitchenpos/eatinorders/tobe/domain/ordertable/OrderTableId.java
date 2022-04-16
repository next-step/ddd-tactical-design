package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;
import java.util.UUID;

public final class OrderTableId {

    private final UUID value;

    public OrderTableId(UUID value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("id는 null 일 수 없습니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTableId)) {
            return false;
        }
        OrderTableId that = (OrderTableId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "OrderTableId{" +
            "value=" + value +
            '}';
    }
}
