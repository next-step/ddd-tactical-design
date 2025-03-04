package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderTableId implements Serializable {
    private UUID id;

    protected OrderTableId() {
    }

    public static OrderTableId generate() {
        return new OrderTableId(UUID.randomUUID());
    }

    public OrderTableId(UUID id) {
        Objects.requireNonNull(id, "ordertable id는 필수 입력 항목입니다");
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableId that = (OrderTableId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
