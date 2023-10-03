package kitchenpos.eatinorders.tobe.domain.ordertable;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTable of(OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        return new OrderTable(UUID.randomUUID(), name, numberOfGuests, occupied);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void clear() {
        numberOfGuests = new NumberOfGuests(0);
        occupied = false;
    }

    public void sit() {
        occupied = true;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (!isOccupied()) {
            throw new IllegalStateException();
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }
}
