package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderTableLoader {

    public OrderTable load(final OrderTable orderTable) {
        verifyOrderTable(orderTable);
        return orderTable;
    }

    private void verifyOrderTable(final OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalStateException("주문 테이블은 비어있을 수 없습니다.");
        }
    }
}
