package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.validator.OrderTableValidator;

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

    public void setNumberOfGuests(NumberOfGuests numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
