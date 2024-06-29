package kitchenpos.eatinorder.tobe.domain.ordertable;

import jakarta.persistence.*;
import kitchenpos.eatinorder.tobe.domain.order.ClearedTable;
import kitchenpos.exception.CanNotChange;

import java.util.Objects;
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

    public static OrderTable of(OrderTableName name) {
        return new OrderTable(UUID.randomUUID(), name, NumberOfGuests.of(0), false);
    }

    protected OrderTable() {
    }

    private OrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNum();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void sitted() {
        this.occupied = true;
    }

    public void cleared(ClearedTable clearedTable) {
        if (clearedTable.isLastOrder(id)) {
            this.occupied = false;
            this.numberOfGuests = numberOfGuests.cleared();
        }
    }


    public void changeNumOfGuests(int num) {
        validateOccupied();
        this.numberOfGuests = NumberOfGuests.of(num);
    }

    private void validateOccupied() {
        if (!this.occupied) {
            throw new CanNotChange("테이블이 빈 상태로 손님 수를 변경할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
