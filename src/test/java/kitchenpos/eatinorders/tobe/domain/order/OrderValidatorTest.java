package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderValidatorTest {
    private OrderValidator orderValidator = new OrderValidator();

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void emptyTable() {
        assertThatThrownBy(() -> orderValidator.validate(OrderTable.empty("1번")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용할 수 없는 테이블 입니다.");
    }
}
