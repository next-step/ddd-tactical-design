package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    private static final String INVALID_TABLE_MESSAGE = "사용할 수 없는 테이블 입니다.";

    public void validate(OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalStateException(INVALID_TABLE_MESSAGE);
        }
    }
}
