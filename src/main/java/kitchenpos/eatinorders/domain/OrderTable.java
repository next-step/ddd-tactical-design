package kitchenpos.eatinorders.domain;

import java.util.UUID;
import javax.persistence.*;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(UUID id, String name) {
        this(id, new OrderTableName(name), new NumberOfGuests(0), false);
    }

    public OrderTable(String name, int numberOfGuests, boolean occupied) {
        this(null, new OrderTableName(name), new NumberOfGuests(numberOfGuests), occupied);
    }

    public OrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void clear() {
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = false;
    }

    public void sit() {
        this.occupied = true;
    }

    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public int getNumberOfGuestsValue() {
        return numberOfGuests.getValue();
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public boolean isOccupied() {
        return occupied;
    }
}
