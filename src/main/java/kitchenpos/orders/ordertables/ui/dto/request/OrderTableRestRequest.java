package kitchenpos.orders.ordertables.ui.dto.request;

import kitchenpos.orders.ordertables.domain.NumberOfGuest;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableName;

public class OrderTableRestRequest {
    final private String name;
    final private int numberOfGuest;
    final private boolean occupied;


    public OrderTableRestRequest(String name, int numberOfGuest, boolean occupied) {
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

    public int getNumberOfGuest() {
        return numberOfGuest;
    }


    public boolean isOccupied() {
        return occupied;
    }

}
