package kitchenpos.eatinorder.tobe.domain;

import jakarta.persistence.*;

import java.util.Objects;
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

    public static OrderTable of(String name) {
        return new OrderTable(UUID.randomUUID(), OrderTableName.of(name), NumberOfGuests.of(0), false);
    }

    private OrderTable(UUID id, OrderTableName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNum();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setSitted() {
        this.occupied = true;
    }

    public void setCleared() {
        this.occupied = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
