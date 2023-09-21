package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(final UUID id, final OrderTableName name, final NumberOfGuests numberOfGuests, final boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public UUID getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public static OrderTable create(final String name, final int numberOfGuests) {
        return new OrderTable(
            UUID.randomUUID(),
            new OrderTableName(name),
            new NumberOfGuests(numberOfGuests),
            false
        );
    }

    public void sit() {
        occupied = true;
    }

    public void clear() {
        numberOfGuests = new NumberOfGuests(0);
        occupied = false;
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (!isOccupied()) {
            throw new IllegalStateException();
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }
}
