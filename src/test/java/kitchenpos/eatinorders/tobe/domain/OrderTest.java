package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.TobeFixtures;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static kitchenpos.TobeFixtures.orderLineItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderTest {

    private Order createOrder;

    @BeforeEach
    void setup() {
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem());
        createOrder = new Order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems, TobeFixtures.orderTable());
    }

    @Test
    @DisplayName("매장 주문을 생성한다.")
    void createOrder() {
        // given
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem());

        // when
        Order order = new Order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems, TobeFixtures.orderTable());

        // then
        assertAll(
                () -> assertThat(order.id()).isNotNull(),
                () -> assertThat(order.type()).isEqualTo(OrderType.EAT_IN),
                () -> assertThat(order.status()).isEqualTo(OrderStatus.WAITING)
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("매장 주문을 생성한다 - 주문 타입이 null일경우 Exception 발생")
    void createOrder(OrderType orderType) {
        // given
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem());

        // when
        // then
        assertThatThrownBy(() -> new Order(orderType, OrderStatus.WAITING, orderLineItems, TobeFixtures.orderTable()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("매장 주문을 생성한다 - 노출되고 있지 않은 메뉴 포함 시 Exception 발생")
    void createOrderIfNotDisplayMenuThrowException() {
        // given
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem(19_000));

        // when
        // then
        assertThatThrownBy(() -> new Order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems, TobeFixtures.orderTable()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("매장 주문을 생성한다 - 사용중인 테이블일 경우 Exception 발생")
    void createOrderIfOccupiedTableThrowException() {
        // given
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem());
        OrderTable orderTable = TobeFixtures.orderTable();
        orderTable.sit();

        // when
        // then
        assertThatThrownBy(() -> new Order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems, orderTable))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("매장 주문을 주문 확인 상태로 변경한다.")
    void acceptOrder() {
        // when
        createOrder.accept();

        // then
        assertThat(createOrder.status()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("매장 주문을 주문 확인 상태로 변경한다 - WAITING 상태가 아닐경우 Exception 발생")
    void acceptOrderThrowException() {
        // when
        createOrder.accept();

        // then
        assertThatThrownBy(() -> createOrder.accept())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("매장 주문을 서빙 상태로 변경한다.")
    void servedOrder() {
        // given
        createOrder.accept();

        // when
        createOrder.served();

        // then
        assertThat(createOrder.status()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    @DisplayName("매장 주문을 서빙 상태로 변경한다 - 주문 확인 상태가 아닐경우 Exception 발생")
    void servedOrderThrowException() {
        // when
        // then
        assertThatThrownBy(() -> createOrder.served())
                .isInstanceOf(IllegalStateException.class);
    }
    @Test
    @DisplayName("매장 주문을 계산 완료 상태로 변경한다.")
    void completedOrder() {
        // given
        createOrder.accept();
        createOrder.served();

        // when
        createOrder.completed();

        // then
        assertThat(createOrder.status()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("매장 주문을 계산 완료 상태로 변경한다 - 서빙 상태가 아닐경우 Exception 발생")
    void completedOrderThrowException() {
        // when
        // then
        assertThatThrownBy(() -> createOrder.completed())
                .isInstanceOf(IllegalStateException.class);
    }
}
