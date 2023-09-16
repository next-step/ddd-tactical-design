package kitchenpos.ordermaster.domain;

import static kitchenpos.ordermaster.domain.OrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class OrderMasterTest {
    private OrderMaster delivery;
    private OrderMaster eatIn;

    @BeforeEach
    void setUp() {
        delivery = new OrderMaster(OrderType.DELIVERY, createOrderLineItems());
        eatIn = new OrderMaster(OrderType.EAT_IN, createOrderLineItems());
    }

    @DisplayName("주문종류가 null 이몬 주문테이블 생성이 안된다.")
    @ParameterizedTest
    @NullSource
    void name(OrderType orderType) {
        assertThatThrownBy(() -> new OrderMaster(orderType, createOrderLineItems()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 유형이 올바르지 않습니다.");
    }

    @DisplayName("주문을 수락 한다.")
    @Test
    void accept1() {
        delivery.accept();
        assertThat(delivery.isSameStatus(OrderStatus.ACCEPTED)).isTrue();
    }

    @DisplayName("주문이 대기 상태가 아니면 수락을 할 수 없다.")
    @Test
    void accept2() {
        delivery.accept();
        delivery.serve();
        assertThatThrownBy(() -> delivery.accept())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("접수 대기 중인 주문만 접수할 수 있다.");
    }

    @DisplayName("주문을 서빙 한다.")
    @Test
    void serve1() {
        delivery.accept();
        delivery.serve();
        assertThat(delivery.isSameStatus(OrderStatus.SERVED)).isTrue();
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serve2() {
        assertThatThrownBy(() -> delivery.serve())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("접수된 주문만 서빙할 수 있다.");
    }

    @DisplayName("주문 배달을 시작 한다.")
    @Test
    void startDelivery1() {
        delivery.accept();
        delivery.serve();
        delivery.startDelivery();
        assertThat(delivery.isSameStatus(OrderStatus.DELIVERING)).isTrue();
    }

    @DisplayName("배달 주문만 배달할 수 있다.")
    @Test
    void startDelivery2() {
        eatIn.accept();
        eatIn.serve();
        assertThatThrownBy(() -> eatIn.startDelivery())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("배달 주문만 배달할 수 있다.");
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @Test
    void startDelivery3() {
        delivery.accept();
        assertThatThrownBy(() -> delivery.startDelivery())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("서빙된 주문만 배달할 수 있다.");
    }

    @DisplayName("주문 배달을 종료 한다.")
    @Test
    void completeDelivery1() {
        delivery.accept();
        delivery.serve();
        delivery.startDelivery();
        delivery.completeDelivery();
        assertThat(delivery.isSameStatus(OrderStatus.DELIVERED)).isTrue();
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @Test
    void completeDelivery2() {
        delivery.accept();
        assertThatThrownBy(() -> delivery.completeDelivery())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("배달 중인 주문만 배달 완료할 수 있다.");
    }

    @DisplayName("주문을 종료 한다.")
    @Test
    void complete1() {
        eatIn.accept();
        eatIn.serve();
        eatIn.complete();
        assertThat(eatIn.isSameStatus(OrderStatus.COMPLETED)).isTrue();
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @Test
    void complete2() {
        delivery.accept();
        delivery.serve();
        assertThatThrownBy(() -> delivery.complete())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.");
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @Test
    void complete3() {
        eatIn.accept();
        assertThatThrownBy(() -> eatIn.complete())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.");
    }
}
