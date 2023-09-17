package kitchenpos.ordertables.dto;

import kitchenpos.ordertables.domain.OrderTable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTableResponse {

    private UUID id;
    private String name;
    private int numberOfGuest;
    private boolean occupied;

    public OrderTableResponse() {
    }

    public OrderTableResponse(UUID orderTableId, String name, int numberOfGuest, boolean occupied) {
        this.id = orderTableId;
        this.name = name;
        this.numberOfGuest = numberOfGuest;
        this.occupied = occupied;
    }

    public static OrderTableResponse fromEntity(OrderTable orderTable) {
        return new OrderTableResponse(
                orderTable.getIdValue(),
                orderTable.getNameValue(),
                orderTable.getNumberOfGuestValue(),
                orderTable.isOccupied()
        );
    }

    public static List<OrderTableResponse> fromEntities(List<OrderTable> orderTables) {
        return orderTables.stream()
                .map(OrderTableResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }


    public String getName() {
        return name;
    }

    public int getNumberOfGuest() {
        return numberOfGuest;
    }

    public UUID getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
