package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected OrderTable() {
    }

    public OrderTable(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = 0;
        this.empty = true;
    }

    private OrderTable(UUID id, String name, int numberOfGuests, boolean empty) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public OrderTable assign(final int numberOfGuests) {
        if(!empty) {
            throw new IllegalStateException("order table is already assigned.");
        }

        return new OrderTable(id, name, numberOfGuests, false);
    }

    public OrderTable clear() {
        if(empty) {
            return this;
        }

        return new OrderTable(id, name, 0, true);
    }

    public OrderTable changeNumberOfGuests(final int numberOfGuests) {
        if(empty) {
            throw new IllegalStateException("cannot change number of guests - order table is empty");
        }

        return new OrderTable(id, name, numberOfGuests, false);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderTable that = (OrderTable) o;
        return numberOfGuests == that.numberOfGuests
                && empty == that.empty
                && id.equals(that.id)
                && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfGuests, empty);
    }
}
