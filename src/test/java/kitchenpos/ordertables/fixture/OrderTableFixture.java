package kitchenpos.ordertables.fixture;

import kitchenpos.ordertables.domain.NumberOfGuest;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableName;

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
