package kitchenpos.eatinorders.tobe.domain;

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

    public OrderTable(String name, int numberOfGuests, boolean occupied) {
        this.id = UUID.randomUUID();
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.occupied = occupied;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getValue();
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests.changeNumberOfGuests(numberOfGuests);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupied() {
        this.occupied = true;
    }

    public void clear() {
        this.occupied = false;
        this.numberOfGuests = new NumberOfGuests(0);
    }
}
