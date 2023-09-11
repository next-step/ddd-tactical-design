package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class ToBeOrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public ToBeOrderTable() {
    }

    public ToBeOrderTable(OrderTableName name, NumberOfGuests numberOfGuests,boolean occupied){
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;

    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getOrderTableName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public void zeroizeNumberOfGuests(NumberOfGuests numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void changeOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
