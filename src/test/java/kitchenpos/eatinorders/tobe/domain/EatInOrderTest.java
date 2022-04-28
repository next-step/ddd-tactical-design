package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.util.ReflectionTestUtils;

import static kitchenpos.TobeFixtures.newOrderLineItem;
import static kitchenpos.TobeFixtures.newOrderLineItems;
import static kitchenpos.TobeFixtures.newOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class EatInOrderTest {

    private OrderLineItems orderLineItems;

    @BeforeEach
    void setUp() {
        OrderLineItem item1 = newOrderLineItem("item1", 1000L, 1L);
        OrderLineItem item2 = newOrderLineItem("item2", 2000L, 2L);
        OrderLineItem item3 = newOrderLineItem("item3", 2000L, 3L);
        orderLineItems = newOrderLineItems(item1, item2, item3);
    }

    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = { "ACCEPTED", "SERVED", "DELIVERING", "DELIVERED", "COMPLETED" })
    @DisplayName("대기 상태가 아니면 접수 상태로 변경 불가능")
    void acceptFail(OrderStatus status) {
        // given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, newOrderTable("order-table"));
        ReflectionTestUtils.setField(eatInOrder, "status", status);

        // when
        assertThatIllegalStateException().isThrownBy(eatInOrder::accept);
    }

    @Test
    @DisplayName("대기 상태이면 접수 상태로 변경 가능")
    void acceptSuccess() {
        // given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, newOrderTable("order-table"));

        // when
        eatInOrder.accept();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }
}
