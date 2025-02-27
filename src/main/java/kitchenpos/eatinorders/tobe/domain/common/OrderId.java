package kitchenpos.eatinorders.tobe.domain.common;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderId implements Serializable {
    private UUID id;

    protected OrderId() {
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    public OrderId(UUID id) {
        Objects.requireNonNull(id, "order id는 필수 입력 항목입니다");
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(id, orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
