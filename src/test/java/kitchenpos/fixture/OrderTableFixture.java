package kitchenpos.fixture;

import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

public class OrderTableFixture {

    public static OrderTable ORDER_TABLE1() {
        return new OrderTable("table1", 1);
    }

}
