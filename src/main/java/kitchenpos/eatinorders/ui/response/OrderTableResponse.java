package kitchenpos.eatinorders.ui.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.OrderTable;

public class OrderTableResponse {

    private final UUID id;
    private final String name;
    private final int numberOfGuests;
    private final boolean occupied;

    public OrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTableResponse from(OrderTable orderTable) {
        return new OrderTableResponse(
            orderTable.getId(),
            orderTable.getNameValue(),
            orderTable.getNumberOfGuestsValue(),
            orderTable.isOccupied()
        );
    }

    public static List<OrderTableResponse> of(List<OrderTable> orderTables) {
        return orderTables.stream()
            .map(OrderTableResponse::from)
            .collect(toList());
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
}
