package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.Order;
import kitchenpos.eatinorders.tobe.domain.order.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static kitchenpos.Fixtures2.orderLineItem;
import static kitchenpos.eatinorders.tobe.domain.order.OrderStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * TODO
 * [] 완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.
 * [] 주문 유형이 올바르지 않으면 등록할 수 없다.
 * [] 주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.
 * [] 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
 * [] 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.
 */
class OrderTest {

    @DisplayName("1개 이상의 OrderLineItem 으로 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> order(orderTable()));
    }

    @DisplayName("orderLineItem이 없으면 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void nullMenu(OrderLineItem... orderLineItems) {
        assertThatThrownBy(() -> order(orderTable(), orderLineItems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문할 메뉴가 존재하지 않습니다.");
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        Order order = order(orderTable(), WAITING);

        order.accept();

        assertThat(order.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("대기 중인 주문이 아니면 접수할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"WAITING"}, value = OrderStatus.class)
    void invalidAccept(OrderStatus status) {
        Order order = order(orderTable(), status);

        assertThatThrownBy(order::accept)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("접수할 수 없는 주문입니다.");
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        Order order = order(orderTable(), ACCEPTED);

        order.serve();

        assertThat(order.getStatus()).isEqualTo(SERVED);
    }

    @DisplayName("접수된 주문이 아니면 서빙할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"ACCEPTED"}, value = OrderStatus.class)
    void invalidServe(OrderStatus status) {
        Order order = order(orderTable(), status);

        assertThatThrownBy(order::serve)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("서빙할 수 없는 주문입니다.");
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        Order order = order(orderTable(), SERVED);

        order.complete();

        assertThat(order.getStatus()).isEqualTo(COMPLETED);
    }

    @DisplayName("서빙된 주문이 아니면 완료할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"SERVED"}, value = OrderStatus.class)
    void invalidComplete(OrderStatus status) {
        Order order = order(orderTable(), status);

        assertThatThrownBy(order::complete)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("완료할 수 없는 주문입니다.");
    }

    private OrderTable orderTable() {
        return OrderTable.of("1번", 4, false);
    }

    private Order order(OrderTable orderTable) {
        return new Order(new OrderTableId(orderTable.getId()), WAITING, orderLineItem());
    }

    private Order order(OrderTable orderTable, OrderLineItem... orderLineItems) {
        return new Order(new OrderTableId(orderTable.getId()), WAITING, orderLineItems);
    }

    private Order order(OrderTable orderTable, OrderStatus status) {
        return new Order(new OrderTableId(orderTable.getId()), status, orderLineItem());
    }
}
