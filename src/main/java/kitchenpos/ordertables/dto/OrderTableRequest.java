package kitchenpos.ordertables.dto;

import kitchenpos.ordertables.domain.NumberOfGuest;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableName;

public class OrderTableRequest {
    private String name;
    private int numberOfGuest;
    private boolean occupied;

    public OrderTableRequest() {
    }

    public OrderTableRequest(String name, int numberOfGuest, boolean occupied) {
        this.name = name;
        this.numberOfGuest = numberOfGuest;
        this.occupied = occupied;
    }

    public OrderTable toEntity() {
        return new OrderTable(
                new OrderTableName(name),
                new NumberOfGuest(numberOfGuest),
                occupied
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfGuest() {
        return numberOfGuest;
    }

    public void setNumberOfGuest(int numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
