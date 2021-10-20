package kitchenpos.fixture;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;

public class OrderTableFixture {
    public static OrderTable 주문테이블() {
        return new OrderTable("주문테이블");
    }

    public static OrderTable 앉은테이블() {
        final OrderTable orderTable = 주문테이블();
        orderTable.sit();
        return orderTable;
    }

    public static OrderTable 앉은테이블(final int numberOfGuests) {
        final OrderTable orderTable = 주문테이블();
        orderTable.sit();
        orderTable.changeNumberOfGuests(numberOfGuests);
        return orderTable;
    }
}
