package kitchenpos.eatinorders.domain.ordertables;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTable createNew(OrderTableName name) {
        return new OrderTable(UUID.randomUUID(), name, NumberOfGuests.ZERO, false);
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.occupied = false;
        this.numberOfGuests = NumberOfGuests.ZERO;
    }

    public void changeNumberOfGuests(NumberOfGuests numberOfGuests) {
        if (this.isClearTable()) {
            throw new IllegalStateException("미사용 상태의 테이블은 손님 수를 변경할 수 없습니다. 테이블을 사용 상태로 변경 후 손님 수를 변경해주세요.");
        }
        this.numberOfGuests = numberOfGuests;
    }

    private boolean isClearTable() {
        return !this.occupied;
    }

    public UUID getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
