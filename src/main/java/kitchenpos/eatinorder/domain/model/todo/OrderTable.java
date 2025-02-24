package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.shared.domain.Profanities;

import java.util.Objects;
import java.util.UUID;

public class OrderTable {
    private final UUID id;
    private final OrderTableName name;
    private NumberOfGuests numberOfGuests;
    private OrderTableOccupiedState orderTableOccupiedState;

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

    public void sit() {
        orderTableOccupiedState = orderTableOccupiedState.occupy();
    }

    public void clear() {
        orderTableOccupiedState = orderTableOccupiedState.vacate();
        numberOfGuests = NumberOfGuests.ZERO;
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (orderTableOccupiedState.isVacant()) {
            throw new IllegalStateException("빈 테이블의 손님 수는 변경할 수 없습니다.");
        }
        this.numberOfGuests = NumberOfGuests.of(numberOfGuests);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
