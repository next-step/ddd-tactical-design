package kitchenpos;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.CreateRequest;

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

    public static CreateRequest createOrderTableRequest(final String name) {
        return new CreateRequest(name);
    }

    public static ChangeNumberOfGuestsRequest changeNumberOfGuestsRequest(final int numberOfGuests) {
        return new ChangeNumberOfGuestsRequest(numberOfGuests);
    }

}
