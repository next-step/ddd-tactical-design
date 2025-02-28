package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.tobe.domain.common.*;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemsException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableException;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderTest {

    @DisplayName("주문 항목에 음수 수량이 포함되어 있으면 예외가 발생한다.")
    @ValueSource(ints = {-1})
    @ParameterizedTest
    void validateQuantity(int negativeQuantity) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000)),
                new OrderLineItem(2L, MenuId.generate(), negativeQuantity, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                OrderStatus.WAITING,
                orderLineItems,
                null,
                OrderTableId.generate()
        );

        assertThatThrownBy(() -> new EatInOrder(order))
                .isInstanceOf(InvalidOrderLineItemsException.class);
    }

    @DisplayName("빈 주문 테이블에서 매장 내 식사 주문을 생성하면 예외 발생한다")
    @ValueSource(booleans = {false})
    @ParameterizedTest
    void createEatInOrderByEmptyTable(boolean occupied) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderTable orderTable = new OrderTable("1번테이블", 0, occupied);
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                null,
                orderLineItems,
                null,
                null
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        assertThatThrownBy(() -> eatInOrder.createOrder(orderTable))
                .isInstanceOf(InvalidOrderTableException.class);
    }

    @DisplayName("매장 내 식사 주문을 생성한다")
    @Test
    void createEatInOrder() {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderTable orderTable = new OrderTable("1번테이블", 4, true);
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                null,
                orderLineItems,
                null,
                null
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        eatInOrder.createOrder(orderTable);

        assertAll(
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTable.getId())
        );
    }

    @DisplayName("대기 중인 주문이 아니면 주문 수락 시 예외 발생한다")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateAcceptStatus(OrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                status,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        assertThatThrownBy(eatInOrder::accept)
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("대기 중인 주문을 수락한다")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void accept(OrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                status,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        eatInOrder.accept();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수된 주문이 아니면 주문 서빙 시 예외 발생한다")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateServeStatus(OrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                status,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        assertThatThrownBy(eatInOrder::serve)
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("접수한 주문을 서빙한다")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void serve(OrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                status,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        eatInOrder.serve();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("서빙된 주문이 아니면 주문 완료 시 예외 발생한다")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateCompleteStatus(OrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                status,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        assertThatThrownBy(eatInOrder::complete)
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("주문을 완료 처리 한다")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void complete(OrderStatus status) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                status,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        eatInOrder.complete();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}
