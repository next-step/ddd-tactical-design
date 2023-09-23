package kitchenpos.orders.ordertables.fixture;

import kitchenpos.orders.ordertables.domain.NumberOfGuest;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableName;

public class OrderTableFixture {

    private OrderTableFixture() {
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(new OrderTableName("1번"),
                new NumberOfGuest(numberOfGuests),
                occupied);
    }
}
