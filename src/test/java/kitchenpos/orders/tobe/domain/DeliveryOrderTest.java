package kitchenpos.orders.tobe.domain;

import static kitchenpos.TobeFixtures.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.common.tobe.infra.TestContainer;

class DeliveryOrderTest {
    private KitchenridersClient kitchenridersClient;

    @BeforeEach
    void setUp() {
        TestContainer testContainer = new TestContainer();
        kitchenridersClient = testContainer.kitchenridersClient;
    }

    @DisplayName("배달 주문을 생성할 수 있다.")
    @Test
    void createDeliveryOrder() {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(OrderStatus.WAITING, "서울시 송파구 위례성대로 2");


        // then
        assertThat(deliveryOrder).isNotNull();
        assertThat(deliveryOrder.getType()).isEqualTo(OrderType.DELIVERY);
        assertThat(deliveryOrder.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2");
    }

    @DisplayName("배달 주소가 없으면 배달 주문을 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createDeliveryOrderWithoutAddress(final String deliveryAddress) {
        // when // then
        assertThatThrownBy(() ->  deliveryOrder(OrderStatus.WAITING, deliveryAddress)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("접수 대기 중인 배달 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void acceptInvalidStatusOrder(OrderStatus status) {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(status, "서울시 송파구 위례성대로 2");

        // when // then
        assertThatThrownBy(() -> deliveryOrder.accepted(kitchenridersClient))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void acceptOrder() {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(OrderStatus.WAITING, "서울시 송파구 위례성대로 2");

        // when
        deliveryOrder.accepted(kitchenridersClient);

        // then
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("조리가 끝난 배달 주문만 배달할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void deliveringInvalidStatusOrder(OrderStatus status) {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(status, "서울시 송파구 위례성대로 2");

        // when // then
        assertThatThrownBy(deliveryOrder::delivering)
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달을 시작한다.")
    @Test
    void delivering() {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(OrderStatus.SERVED, "서울시 송파구 위례성대로 2");

        // when
        deliveryOrder.delivering();

        // then
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryInvalidStatusOrder(OrderStatus status) {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(status, "서울시 송파구 위례성대로 2");

        // when // then
        assertThatThrownBy(deliveryOrder::delivered)
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 완료한다.")
    @Test
    void completeDelivery() {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(OrderStatus.DELIVERING, "서울시 송파구 위례성대로 2");

        // when
        deliveryOrder.delivered();

        // then
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @DisplayName("배달 완료인 경우에만 배달 주문을 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeInvalidStatusOrder(OrderStatus status) {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(status, "서울시 송파구 위례성대로 2");

        // when // then
        assertThatThrownBy(deliveryOrder::completed)
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 완료한다.")
    @Test
    void completeDeliveryOrder() {
        // given
        DeliveryOrder deliveryOrder = deliveryOrder(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2");

        // when
        deliveryOrder.completed();

        // then
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}

