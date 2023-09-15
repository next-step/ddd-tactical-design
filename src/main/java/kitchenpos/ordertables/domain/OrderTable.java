package kitchenpos.ordertables.domain;


import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @EmbeddedId
    private OrderTableId id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuest numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public OrderTable() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean occupied) {
        this.occupied = occupied;
    }
}
