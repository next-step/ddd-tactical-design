package kitchenpos.eatinorders.todo.domain.ordertables;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;

import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.ORDER_TABLE_UNOCCUPIED_STATUS;

@Table(name = "order_table")
@Entity
public class OrderTable {
    public static final boolean OCCUPIED = true;
    public static final boolean UNOCCUPIED = false;

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private NumberOfGuests numberOfGuests = NumberOfGuests.ZERO_NUMBER_OF_GUESTS;

    @Column(name = "occupied", nullable = false)
    private boolean occupied = UNOCCUPIED;

    protected OrderTable() {
    }

    protected OrderTable(UUID id, OrderTableName name) {
        this.id = id;
        this.name = name;
    }

    public OrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTable from(OrderTableName name) {
        return new OrderTable(UUID.randomUUID(), name);
    }

    public static OrderTable from(OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        return new OrderTable(UUID.randomUUID(), name, numberOfGuests, occupied);
    }

    public void sit() {
        this.occupied = OCCUPIED;
    }

    public void clear() {
        changeNumberOfGuests(NumberOfGuests.ZERO_NUMBER_OF_GUESTS);
        this.occupied = UNOCCUPIED;
    }

    public void changeNumberOfGuests(NumberOfGuests numberOfGuests) {
        checkClear();
        this.numberOfGuests = numberOfGuests;
    }

    public void checkClear() {
        if (this.isOccupied() == UNOCCUPIED) {
            throw new KitchenPosIllegalStateException(ORDER_TABLE_UNOCCUPIED_STATUS, this.id);
        }
    }

    public UUID getId() {
        return id;
    }

    public String name() {
        return name.nameValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.number();
    }

    public boolean isOccupied() {
        return occupied;
    }
}
