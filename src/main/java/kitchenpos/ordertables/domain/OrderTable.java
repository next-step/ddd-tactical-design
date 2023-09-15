package kitchenpos.ordertables.domain;


import kitchenpos.ordertables.exception.OrderTableErrorCode;
import kitchenpos.ordertables.exception.OrderTableException;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @EmbeddedId
    private OrderTableId id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuest numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(OrderTableName name, NumberOfGuest numberOfGuests, boolean occupied) {
        this.id = new OrderTableId();
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void changeNumberOfGuest(NumberOfGuest numberOfGuest) {
        if(!occupied){
            throw new OrderTableException(OrderTableErrorCode.NON_OCCUPIED_CANNOT_CHANGE_NUMBER_OF_GUESTS);
        }
        this.numberOfGuests = numberOfGuest;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public UUID getIdValue() {
        return id.getId();
    }

    public String getNameValue() {
        return name.getValue();
    }

    public int getNumberOfGuestValue() {
        return numberOfGuests.getValue();
    }

    public void clear() {
        this.numberOfGuests = NumberOfGuest.ZERO;
        this.occupied = false;
    }

    public void sit() {
        this.occupied = true;
    }
}
