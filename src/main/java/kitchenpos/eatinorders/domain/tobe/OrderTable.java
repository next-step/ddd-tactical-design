package kitchenpos.eatinorders.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(String name) {
        this(UUID.randomUUID(), name, 0, false);
    }

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = new OrderTableName(name);
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void sit() {
        occupied = true;
    }

    public void guests(int guests) {
        if (!isOccupied()) {
            throw new IllegalStateException();
        }
        if (guests < 0) {
            throw new IllegalArgumentException();
        }
        numberOfGuests = guests;
    }

    public void clear(OrderManager orderManager) {
        if (orderManager.isExistNotComplateOrder(this)) {
            throw new IllegalStateException();
        }
        clear();
    }

    public void clear() {
        numberOfGuests = 0;
        occupied = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
