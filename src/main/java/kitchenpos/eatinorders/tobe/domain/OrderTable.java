package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private OrderTableName name;

    @Embedded
    @Column(name = "number_of_guests", nullable = false)
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(OrderTableName name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = false;
    }

    public UUID id() {
        return id;
    }

    public OrderTableName name() {
        return name;
    }

    public String nameValue() {
        return this.name.name();
    }

    public NumberOfGuests numberOfGuests() {
        return numberOfGuests;
    }

    public int numberOfGuestsValue() {
        return numberOfGuests.numberOfGuests();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = false;
    }

    public void changeNumberOfGuest(int numberOfGuest) {
        emptyTableCheck();
        this.numberOfGuests.changeNumberOfGuest(numberOfGuest);
    }

    private void emptyTableCheck() {
        if (!this.occupied) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTable that = (OrderTable) o;
        return occupied == that.occupied && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfGuests, that.numberOfGuests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfGuests, occupied);
    }
}
