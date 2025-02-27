package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.tobe.domain.common.*;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemsException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableException;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        OrderEntity orderEntity = eatInOrder.toEntity();
        assertThat(orderEntity.status()).isEqualTo(OrderStatus.WAITING);
        assertThat(orderEntity.orderTableId()).isEqualTo(orderTable.getId());
    }

    @DisplayName("매장 내 식사의 주문 상태를 변경한다")
    @CsvSource({
            "WAITING, ACCEPTED",
            "ACCEPTED, SERVED",
            "SERVED, COMPLETED"
    })
    @ParameterizedTest
    void changeStatus(OrderStatus current, OrderStatus next) {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
        );
        OrderEntity order = new OrderEntity(
                OrderType.EAT_IN,
                current,
                orderLineItems,
                null,
                OrderTableId.generate()
        );
        EatInOrder eatInOrder = new EatInOrder(order);

        eatInOrder.changeStatus();

        OrderEntity orderEntity = eatInOrder.toEntity();
        assertThat(orderEntity.status()).isEqualTo(next);
    }
}
