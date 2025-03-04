package kitchenpos.eatinorders.presentation.dto;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.OrderTableName;

public class OrderTableCreateResponse {

    private OrderTableId id;

    private OrderTableName name;

    private PositiveNumber numberOfGuests;

    private boolean occupied;

    public static OrderTableCreateResponse from(OrderTable orderTable) {
        return new OrderTableCreateResponse(
                orderTable.getId(),
                orderTable.getName(),
                orderTable.getNumberOfGuests(),
                orderTable.isOccupied()
        );
    }


    public OrderTableCreateResponse(OrderTableId id, OrderTableName name, PositiveNumber numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTableId getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public PositiveNumber getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
