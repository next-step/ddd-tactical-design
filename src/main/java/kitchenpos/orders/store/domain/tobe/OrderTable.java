package kitchenpos.orders.store.domain.tobe;

import jakarta.persistence.*;

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

    protected OrderTable() {
    }

    public OrderTable(String name) {
        this.id = UUID.randomUUID();
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = false;
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = false;
    }

    public void changeNumberOfGuests(NumberOfGuests numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean hasGuest() {
        return numberOfGuests.getNumberOfGuests() > 0;
    }
}
