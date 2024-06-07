package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.vo.OrderTableName;

import java.util.UUID;

@Table(name = "order_table2")
@Entity(name = "OrderTable2")
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

    protected OrderTable() {}

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        checkNumberOfGuests(numberOfGuests);
        checkOccupied(occupied);
        this.id = id;
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.occupied = occupied;
    }

    private void checkNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests != 0) {
            throw new IllegalArgumentException();
        }
    }

    private void checkOccupied(boolean occupied) {
        if (occupied) {
            throw new IllegalArgumentException();
        }
    }

    public void sit() {
        occupied = true;
    }

    public void clear() {
        numberOfGuests = NumberOfGuests.zero();
        occupied = false;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        NumberOfGuests inputNumberOfGuests = new NumberOfGuests(numberOfGuests);
        if (isNotOccupied()) {
            throw new IllegalStateException();
        }
        this.numberOfGuests = inputNumberOfGuests;
    }

    public boolean isNotOccupied() {
        return !occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public UUID getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

}
