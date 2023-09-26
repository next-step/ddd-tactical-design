package kitchenpos.order.domain;

import kitchenpos.order.eatinorders.domain.vo.OrderTableName;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public OrderTable() {
    }

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = new OrderTableName(name);
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable(OrderTable orderTable) {
        this(orderTable.getId(),
                orderTable.getName().getName(),
                orderTable.getNumberOfGuests(),
                orderTable.isOccupied());
    }

    public OrderTable sit() {
        this.occupied = true;
        return this;
    }

    public OrderTable clear() {
        this.numberOfGuests = 0;
        this.occupied = false;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public OrderTable setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
