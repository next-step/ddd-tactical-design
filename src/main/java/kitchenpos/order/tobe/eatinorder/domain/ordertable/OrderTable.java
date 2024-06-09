package kitchenpos.order.tobe.eatinorder.domain.ordertable;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity(name = "newOrderTable")
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private TableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(UUID id, String name) {
        this.id = id;
        this.name = new TableName(name);
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = false;
    }

    public void clear() {
        this.occupied = false;
        this.numberOfGuests = new NumberOfGuests(0);
    }

    public void sit() {
        this.occupied = true;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (!this.isOccupied()) {
            throw new IllegalStateException("테이블에 착석 후 손님 수를 변경할 수 있습니다.");
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTable orderTable)) {
            return false;
        }

        return this.getId() != null && Objects.equals(this.getId(), orderTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
