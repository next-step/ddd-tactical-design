package kitchenpos.eatinorders.tobe.domain.ordertable;


import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;

public final class OrderTable {

    private final OrderTableId id;
    private final DisplayedName name;
    private int numberOfGuests;
    private boolean empty;

    public OrderTable(
        OrderTableId id,
        DisplayedName name,
        int numberOfGuests,
        boolean empty
    ) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public OrderTable(
        UUID id,
        DisplayedName name
    ) {
        this(new OrderTableId(id), name, 0, true);
    }

    public void sit() {
        if (!empty) {
            throw new IllegalStateException("비어있지 않은 테이블은 앉을 수 없습니다.");
        }
        this.empty = false;
    }

    public void clear() {
        this.empty = true;
        this.numberOfGuests = 0;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (empty) {
            throw new IllegalStateException("주문 테이블이 비어있는 상태에서는 손님 수를 변경할 수 없습니다.");
        }
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("변경할 손님의 수는 0명보다 커야 합니다 numberOfGuests:" + numberOfGuests);
        }
        this.numberOfGuests = numberOfGuests;
    }
}
