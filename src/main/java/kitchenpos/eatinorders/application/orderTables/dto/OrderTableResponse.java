package kitchenpos.eatinorders.application.orderTables.dto;

import kitchenpos.eatinorders.domain.ordertables.OrderTable;

public class OrderTableResponse {
    private String id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;

    public OrderTableResponse(String id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTableResponse from(OrderTable orderTable) {
        return new OrderTableResponse(
                orderTable.getId().toString(),
                orderTable.getName().getValue(),
                orderTable.getNumberOfGuests().getValue(),
                orderTable.isOccupied()
        );
    }

    public String getId() {
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
}
