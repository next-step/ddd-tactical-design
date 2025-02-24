package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.shared.domain.Profanities;

import java.util.UUID;

public class OrderTable {
    private final UUID id;
    private final OrderTableName name;
    private final NumberOfGuests numberOfGuests;
    private final OrderTableOccupiedState orderTableOccupiedState;

    private OrderTable(
            final UUID id,
            final OrderTableName name,
            final NumberOfGuests numberOfGuests,
            final OrderTableOccupiedState orderTableOccupiedState
    ) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.orderTableOccupiedState = orderTableOccupiedState;
    }

    public static OrderTable createEmptyTable(
            final UUID id,
            final String name,
            final Profanities profanities
            ) {
        return new OrderTable(
                id,
                OrderTableName.of(name, profanities),
                NumberOfGuests.ZERO,
                OrderTableOccupiedState.VACANT
        );
    }

    public boolean isEmpty() {
        return numberOfGuests.isZero() && orderTableOccupiedState.isVacant();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.value();
    }

    public boolean isOccupied() {
        return orderTableOccupiedState.isOccupied();
    }
}
