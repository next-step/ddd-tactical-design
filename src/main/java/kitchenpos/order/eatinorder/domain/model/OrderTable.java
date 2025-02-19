package kitchenpos.order.eatinorder.domain.model;

import jakarta.persistence.*;

import java.util.UUID;

import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.EMPTY_ORDER_TABLE_EXCEPTION;
import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.NUMBER_OF_GUESTS_EXCEPTION;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Enumerated
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public OrderTable() {
    }

    public OrderTable(UUID id, OrderTableName name, int numberOfGuests, boolean occupied) {
        validateNumberOfGuests(numberOfGuests);
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable(UUID id, OrderTableName name) {
        this(id, name, 0, false);
    }

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        this(id, new OrderTableName(name), numberOfGuests, occupied);
    }

    public OrderTable(String name, int numberOfGuests, boolean occupied) {
        this(UUID.randomUUID(), name, numberOfGuests, occupied);
    }

    private void validateNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException(NUMBER_OF_GUESTS_EXCEPTION.getMessage());
        }
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        validateTableIsOccupied();
        validateNumberOfGuests(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void validateTableIsOccupied() {
        if (!isOccupied()) {
            throw new IllegalStateException(EMPTY_ORDER_TABLE_EXCEPTION.getMessage());
        }
    }

    public void releaseTable() {
        this.numberOfGuests = 0;
        this.occupied = false;
    }

    public void occupyTable() {
        this.occupied = true;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getInnerName() {
        return name.getValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

//    public void setOccupied(final boolean occupied) {
//        this.occupied = occupied;
//    }

//    public void setName(final String name) {
//        this.name = name;
//    }
}
