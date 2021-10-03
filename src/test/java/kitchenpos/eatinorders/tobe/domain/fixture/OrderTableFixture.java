package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.eatinorders.tobe.domain.model.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

public class OrderTableFixture {

    public static OrderTable ORDER_TABLE_FIXTURE(NumberOfGuests numberOfGuests) {
        return new OrderTable("1번", numberOfGuests);
    }

}
