package kitchenpos.eatinorders.tobe.domain;

import java.util.Arrays;
import java.util.UUID;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.test.util.ReflectionTestUtils;

import static kitchenpos.TobeFixtures.newOrder;
import static kitchenpos.TobeFixtures.newOrderLineItem;
import static kitchenpos.TobeFixtures.newOrderLineItems;
import static kitchenpos.TobeFixtures.newOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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

    @Test
    @DisplayName("주문 테이블 ID가 없으면 생성 실패")
    void createOrderFail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new EatInOrder(orderLineItems, null));
    }

    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = { "ACCEPTED", "SERVED", "DELIVERING", "DELIVERED", "COMPLETED" })
    @DisplayName("대기 상태가 아니면 접수 상태로 변경 불가능")
    void acceptFail(OrderStatus status) {
        // given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, UUID.randomUUID());
        ReflectionTestUtils.setField(eatInOrder, "status", status);

        // when
        assertThatIllegalStateException().isThrownBy(eatInOrder::accept);
    }

    @Test
    @DisplayName("대기 상태이면 접수 상태로 변경 가능")
    void acceptSuccess() {
        // given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, UUID.randomUUID());

        // when
        eatInOrder.accept();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = { "WAITING", "SERVED", "DELIVERING", "DELIVERED", "COMPLETED" })
    @DisplayName("접수 상태가 아니면 서빙 상태로 변경 불가능")
    void serveFail(OrderStatus status) {
        // given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, UUID.randomUUID());
        ReflectionTestUtils.setField(eatInOrder, "status", status);

        // when
        assertThatIllegalStateException().isThrownBy(eatInOrder::serve);
    }

    @Test
    @DisplayName("접수 상태이면 서빙 상태로 변경 가능")
    void serveSuccess() {
        // given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, UUID.randomUUID());
        eatInOrder.accept();

        // when
        eatInOrder.serve();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    @DisplayName("유효한 주문 테이블이 아니면 완료 상태로 변경 불가능")
    void completeFail01() {
        // given
        OrderTable orderTable = newOrderTable("order-table");
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, UUID.randomUUID());

        // when
        assertThatIllegalStateException().isThrownBy(() -> eatInOrder.complete(orderTable));
    }

    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = { "WAITING", "ACCEPTED", "DELIVERING", "DELIVERED", "COMPLETED" })
    @DisplayName("서빙 상태가 아니면 완료 상태로 변경 불가능")
    void completeFail02(OrderStatus status) {
        // given
        OrderTable orderTable = newOrderTable("order-table");
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable.getId());
        ReflectionTestUtils.setField(eatInOrder, "status", status);

        // when
        assertThatIllegalStateException().isThrownBy(() -> eatInOrder.complete(orderTable));
    }

    @Test
    @DisplayName("서빙 상태이면 완료 상태로 변경 가능")
    void completeSuccess01() {
        // given
        OrderTable orderTable = newOrderTable("order-table");

        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable.getId());
        eatInOrder.accept();
        eatInOrder.serve();

        // when
        eatInOrder.complete(orderTable);

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("서빙 상태이면 완료 상태로 변경 가능하며, 같은 주문 테이블에서 모든 주문이 완료된 경우 주문 테이블을 clear한다.")
    void completeSuccess02() {
        // given
        OrderTable orderTable = newOrderTable("order-table", Arrays.asList(newOrder(), newOrder(), newOrder()));
        orderTable.getEatInOrders().forEach(order -> {
            order.accept();
            order.serve();
            order.complete(orderTable);
        });

        orderTable.sit();
        orderTable.changeNumberOfGuests(5);

        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable.getId());
        eatInOrder.accept();
        eatInOrder.serve();

        // when
        eatInOrder.complete(orderTable);

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(orderTable.getNumberOfGuests()).isZero();
        assertThat(orderTable.isEmpty()).isTrue();
    }
}
