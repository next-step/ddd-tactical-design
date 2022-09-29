package kitchenpos.eatinorders;

import kitchenpos.eatinorders.tobe.domain.OrderTable;

public final class TobeFixtures {
    public static OrderTable orderTable() {
        return new OrderTable("테이블");
    }
}
