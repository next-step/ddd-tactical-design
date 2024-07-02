package kitchenpos.fixture;

import kitchenpos.orders.store.domain.tobe.OrderTable;

public class OrderTableFixture {

    public static OrderTable createNumber1() {
        return new OrderTable("1번 테이블");
    }
}
