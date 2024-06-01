package kitchenpos.eatinorders.todo.domain;

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

    @Column(name = "name", nullable = false)
    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests = 0;

    @Column(name = "occupied", nullable = false)
    private boolean occupied = false;

    protected OrderTable() {
    }

    protected OrderTable(UUID id, OrderTableName name) {
        this.id = id;
        this.name = name;
    }

    public OrderTable(UUID id, OrderTableName name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTable from(OrderTableName name) {
        return new OrderTable(UUID.randomUUID(), name);
    }

    public static OrderTable from(OrderTableName name, int numberOfGuests, boolean occupied) {
        return new OrderTable(UUID.randomUUID(), name, numberOfGuests, occupied);
    }

    public UUID getId() {
        return id;
    }

    public String name() {
        return name.nameValue();
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
