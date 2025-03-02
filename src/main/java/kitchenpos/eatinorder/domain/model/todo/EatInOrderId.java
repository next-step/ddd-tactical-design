package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderId {
    private final UUID id;

    private EatInOrderId(final UUID id) {
        this.id = id;
    }

    public static EatInOrderId of(final UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("매장 식사 주문 ID를 입력하세요.");
        }
        return new EatInOrderId(id);
    }

    public UUID value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderId that = (EatInOrderId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
