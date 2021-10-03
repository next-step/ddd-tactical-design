package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    private boolean empty;

    protected OrderTable() {

    }

    public OrderTable(String name, NumberOfGuests numberOfGuests) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = true;
    }

    public UUID getId() {
        return this.id;
    }

    public NumberOfGuests getNumberOfGuests() {
        return this.numberOfGuests;
    }

    public void makeEmptyTable() {
        this.numberOfGuests = new NumberOfGuests(0L);
        clearTable();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUpTable() {
        this.empty = false;
    }

    public void clearTable() {
        this.empty = true;
    }

    public boolean isEmpty() {
        return this.empty;
    }

}
