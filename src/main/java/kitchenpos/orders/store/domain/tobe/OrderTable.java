package kitchenpos.orders.store.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
        this(new OrderTableName(name));
    }

    public OrderTable(OrderTableName name) {
        this.id = UUID.randomUUID();
        this.name = name;
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
        if (!isOccupied()) {
            throw new IllegalStateException();
        }
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean hasGuest() {
        return numberOfGuests.getNumberOfGuests() > 0;
    }

    public UUID getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }
}
