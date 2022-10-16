package kitchenpos.eatinorders.domain;

import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrderTable;
import static kitchenpos.eatinorders.domain.EatInOrderStatus.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void acceptException() {
        EatInOrderTable eatInOrderTable = eatInOrderTable(true, 4);
        EatInOrder eatInOrder = eatInOrder(SERVED, eatInOrderTable);
        assertThatThrownBy(eatInOrder::accept)
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("주문상태가 '대기중'이 아니라 '접수됨'으로 변경할 수 없습니다.");
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serveException() {
        EatInOrderTable eatInOrderTable = eatInOrderTable(true, 4);
        EatInOrder eatInOrder = eatInOrder(SERVED, eatInOrderTable);
        assertThatThrownBy(eatInOrder::serve)
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("주문상태가 '접수됨'이 아니라 '서빙됨'으로 변경할 수 없습니다.");
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @Test
    void completeException() {
        EatInOrderTable eatInOrderTable = eatInOrderTable(true, 4);
        EatInOrder eatInOrder = eatInOrder(WAITING, eatInOrderTable);
        assertThatThrownBy(eatInOrder::complete)
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("주문상태가 '서빙됨'이 아니라 '완료'로 변경할 수 없습니다.");
    }
}
