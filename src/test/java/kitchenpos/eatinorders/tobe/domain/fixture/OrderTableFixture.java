package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.eatinorders.tobe.domain.model.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

import java.util.UUID;

public class OrderTableFixture {

    public static OrderTable DEFAULT_ORDER_TABLE() {
        return new OrderTable(UUID.randomUUID(), new DisplayedName("1번 테이블"), new NumberOfGuests(0L));
    }
}
