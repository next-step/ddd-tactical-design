package kitchenpos.apply.order.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName orderTableName;

    @Embedded
    private NumberOfGuest numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() { }

    public static OrderTable of(String name) {
        return new OrderTable(name);
    }

    public OrderTable(String name) {
        this.id = UUID.randomUUID();
        this.orderTableName = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuest(0);
        this.occupied = false;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return orderTableName.value();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.value();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.numberOfGuests.clean();
        this.occupied = false;
    }

    public void changeNumberOfGuest(int numberOfGuests) {
        this.numberOfGuests = new NumberOfGuest(numberOfGuests);
    }
}
