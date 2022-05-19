package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.tobe.domain.EatInOrderFixture.eatInOrder;
import static org.assertj.core.api.Assertions.assertThat;

class EatInOrdersTest {

    @DisplayName("모든 매장 주문이 완료되었으면 TRUE를 반환한다")
    @Test
    void checkAllCompleted() {
        // given
        EatInOrders eatInOrders = new EatInOrders();
        eatInOrders.add(eatInOrder(OrderStatus.COMPLETED));
        eatInOrders.add(eatInOrder(OrderStatus.COMPLETED));
        eatInOrders.add(eatInOrder(OrderStatus.COMPLETED));

        // when
        boolean completed = eatInOrders.checkAllCompleted();

        // then
        assertThat(completed).isTrue();
    }

    @DisplayName("모든 매장 주문이 완료되지 않았으면 FALSE를 반환한다")
    @Test
    void checkAllNotCompleted() {
        // given
        EatInOrders eatInOrders = new EatInOrders();
        eatInOrders.add(eatInOrder(OrderStatus.COMPLETED));
        eatInOrders.add(eatInOrder(OrderStatus.COMPLETED));
        eatInOrders.add(eatInOrder(OrderStatus.ACCEPTED));

        // when
        boolean completed = eatInOrders.checkAllCompleted();

        // then
        assertThat(completed).isFalse();
    }
}
