package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;
import java.util.UUID;

public final class OrderId {

    private final UUID value;

    public OrderId(UUID value) {
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
        if (!(o instanceof OrderId)) {
            return false;
        }
        OrderId orderId = (OrderId) o;
        return Objects.equals(value, orderId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "OrderId{" +
            "value=" + value +
            '}';
    }
}
