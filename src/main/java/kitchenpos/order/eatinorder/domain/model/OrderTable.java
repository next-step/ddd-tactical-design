package kitchenpos.order.eatinorder.domain.model;

import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.EMPTY_ORDER_TABLE_EXCEPTION;
import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.NUMBER_OF_GUESTS_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

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

    public OrderTable(OrderTableName name, int numberOfGuests, boolean occupied) {
        validateNumberOfGuests(numberOfGuests);
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable(OrderTableName name) {
        this(name, 0, false);
    }

    public OrderTable(String name, int numberOfGuests, boolean occupied) {
        this(new OrderTableName(name), numberOfGuests, occupied);
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

    public void validateTableIsOccupied() {
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

    public OrderTableName getName() {
        return name;
    }
}
