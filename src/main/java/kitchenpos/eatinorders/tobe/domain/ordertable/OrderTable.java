package kitchenpos.eatinorders.tobe.domain.ordertable;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.OrderTableId;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @EmbeddedId
    private OrderTableId id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Embedded
    private Empty empty;

    protected OrderTable() {
    }

    public OrderTable(
        final OrderTableId id,
        final DisplayedName displayedName
    ) {
        this.id = id;
        this.displayedName = displayedName;
        this.numberOfGuests = new NumberOfGuests(0);
        this.empty = new Empty(true);
    }

    public void changeNumberOfGuests(final NumberOfGuests numberOfGuests) {
        if (this.empty.isEmpty()) {
            throw new IllegalStateException("빈 테이블은 방문한 손님 수를 변경할 수 없습니다.");
        }

        this.numberOfGuests = numberOfGuests;
    }

    public boolean isTaken() {
        return this.empty.isTaken();
    }

    public void take() {
        if (isTaken()) {
            throw new IllegalStateException();
        }
        this.empty = new Empty(false);
    }

    public boolean isEmpty() {
        return this.empty.isEmpty();
    }

    public void clear(final OrderTableValidator orderTableValidator) {
        orderTableValidator.validate(this);

        if (isEmpty()) {
            throw new IllegalStateException();
        }

        this.numberOfGuests = new NumberOfGuests(0);
        this.empty = new Empty(true);
    }
}
