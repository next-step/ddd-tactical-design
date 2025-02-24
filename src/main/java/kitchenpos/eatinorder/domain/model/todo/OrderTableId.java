package kitchenpos.eatinorder.domain.model.todo;

import java.util.UUID;

public class OrderTableId {
    private final UUID id;

    private OrderTableId(final UUID id) {
        this.id = id;
    }

    public static OrderTableId of(final UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("주문 테이블 ID를 입력하세요.");
        }
        return new OrderTableId(id);
    }

    public UUID value() {
        return id;
    }
}
