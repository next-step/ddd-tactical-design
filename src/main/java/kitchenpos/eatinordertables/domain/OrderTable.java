package kitchenpos.eatinordertables.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity(name = "TobeOrderTable")
public class OrderTable {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected OrderTable() {}

    public OrderTable(final OrderTableName name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = new NumberOfGuests(0);
        this.empty = true;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void sit() {
        this.empty = false;
    }

    public void clear(final boolean isOrderCompleted) {
        if (!isOrderCompleted) {
            throw new IllegalStateException("주문이 완료된 테이블만 치울 수 있습니다.");
        }
        this.numberOfGuests = new NumberOfGuests(0);
        this.empty = true;
    }

    public void changeNumberOfGuests(final NumberOfGuests numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalStateException("비어있는 테이블은 손님 수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }
}
