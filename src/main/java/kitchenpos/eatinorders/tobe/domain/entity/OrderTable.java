package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.vo.OrderTableName;
import kitchenpos.eatinorders.tobe.domain.constant.OrderTableStatus;

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

    @Transient
    private OrderTableStatus status;

    protected OrderTable() {}

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.occupied = occupied;
        status = OrderTableStatus.EMPTY_TABLE;
        checkNumberAtInitialize(numberOfGuests);
    }

    private void checkNumberAtInitialize(int numberOfGuests) {
        if (status == OrderTableStatus.EMPTY_TABLE && numberOfGuests != 0) {
            throw new IllegalArgumentException();
        }
    }

    public void sit() {
        occupied = true;
        status = OrderTableStatus.SIT_TABLE;
    }

    public void clear() {
        numberOfGuests = NumberOfGuests.zero();
        occupied = false;
        status = OrderTableStatus.EMPTY_TABLE;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        NumberOfGuests inputNumberOfGuests = new NumberOfGuests(numberOfGuests);
        if (status != OrderTableStatus.SIT_TABLE) {
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
