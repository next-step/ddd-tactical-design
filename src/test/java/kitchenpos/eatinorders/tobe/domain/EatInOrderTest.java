package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableException;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderTest {

    @DisplayName("빈 주문 테이블에서 매장 내 식사 주문을 생성하면 예외 발생한다")
    @ValueSource(booleans = {false})
    @ParameterizedTest
    void createEatInOrderByEmptyTable(boolean occupied) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderTable orderTable = new OrderTable("1번테이블", 0, occupied);
        EatInOrder eatInOrder = EatInOrder.create(orderLineItems, null);

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
        EatInOrder eatInOrder = EatInOrder.create(orderLineItems, orderTable.getId());

        eatInOrder.createOrder(orderTable);

        assertAll(
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTable.getId())
        );
    }

    @DisplayName("대기 중인 주문이 아니면 주문 수락 시 예외 발생한다")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateAcceptStatus(EatInOrderStatus status) {
        EatInOrderLineItems orderLineItems = createOrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        EatInOrder eatInOrder = new EatInOrder(OrderId.generate(), status, orderLineItems, OrderTableId.generate(), LocalDateTime.now());

        assertThatThrownBy(eatInOrder::accept)
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("대기 중인 주문을 수락한다")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void accept(EatInOrderStatus status) {
        EatInOrderLineItems orderLineItems = createOrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        EatInOrder eatInOrder = new EatInOrder(OrderId.generate(), status, orderLineItems, OrderTableId.generate(), LocalDateTime.now());

        eatInOrder.accept();

        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수된 주문이 아니면 주문 서빙 시 예외 발생한다")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateServeStatus(EatInOrderStatus status) {
        EatInOrderLineItems orderLineItems = createOrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        EatInOrder eatInOrder = new EatInOrder(OrderId.generate(), status, orderLineItems, OrderTableId.generate(), LocalDateTime.now());

        assertThatThrownBy(eatInOrder::serve)
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("접수한 주문을 서빙한다")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void serve(EatInOrderStatus status) {
        EatInOrderLineItems orderLineItems = createOrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        EatInOrder eatInOrder = new EatInOrder(OrderId.generate(), status, orderLineItems, OrderTableId.generate(), LocalDateTime.now());

        eatInOrder.serve();

        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("서빙된 주문이 아니면 주문 완료 시 예외 발생한다")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateCompleteStatus(EatInOrderStatus status) {
        EatInOrderLineItems orderLineItems = createOrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        EatInOrder eatInOrder = new EatInOrder(OrderId.generate(), status, orderLineItems, OrderTableId.generate(), LocalDateTime.now());

        assertThatThrownBy(eatInOrder::complete)
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("주문을 완료 처리 한다")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void complete(EatInOrderStatus status) {
        EatInOrderLineItems orderLineItems = createOrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        EatInOrder eatInOrder = new EatInOrder(OrderId.generate(), status, orderLineItems, OrderTableId.generate(), LocalDateTime.now());

        eatInOrder.complete();

        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }

    private EatInOrderLineItems createOrderLineItems(OrderLineItem... orderLineItems) {
        return new EatInOrderLineItems(new OrderLineItems(Arrays.asList(orderLineItems)));
    }
}
