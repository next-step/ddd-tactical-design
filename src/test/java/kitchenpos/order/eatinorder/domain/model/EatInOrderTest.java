package kitchenpos.order.eatinorder.domain.model;

import kitchenpos.menu.domain.model.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.TestFixtureFactory.createEatInOrderRequestWithEmptyTable;
import static kitchenpos.TestFixtureFactory.createMenuWithProductAndGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderTest {

    @Test
    @DisplayName("다음 매장 주문 순서를 요청하면 주문 순서를 검증하고 주문 순서를 변경한다.")
    void process_order_flow() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        EatInOrder eatInOrder = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.WAITING);

        // when
        eatInOrder.processOrderFlow(EatInOrderStatus.ACCEPTED);

        // then
        assertThat(eatInOrder.getEatInOrderFlow().name()).isEqualTo(EatInOrderStatus.ACCEPTED.name());
    }

    @Test
    @DisplayName("주문 순서가 맞지 않으면 예외를 던진다.")
    void process_order_flow_exception() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        EatInOrder eatInOrder = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.ACCEPTED);

        // when // then
        assertThatThrownBy(() -> eatInOrder.processOrderFlow(EatInOrderStatus.COMPLETED))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잘못된 매장 주문 순서입니다. 주문 순서를 지켜주세요!");
    }
}
