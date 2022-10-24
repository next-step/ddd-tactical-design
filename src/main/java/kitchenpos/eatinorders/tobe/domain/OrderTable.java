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
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    private String name;

    private int numberOfGuests;
    private boolean occupied;

    public OrderTable(final String name, final int numberOfGuests, final boolean occupied) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable(final int numberOfGuests, final boolean occupied) {
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable() {
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

    public boolean isOccupied() {
        return occupied;
    }

    public void makeEmpty() {
        numberOfGuests = 0;
        occupied = false;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setOccupied(final boolean occupied) {
        this.occupied = occupied;
    }
}
