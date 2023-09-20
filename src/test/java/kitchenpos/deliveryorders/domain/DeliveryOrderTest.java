package kitchenpos.deliveryorders.domain;

import static kitchenpos.deliveryorders.domain.DeliveryOrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.deliveryorders.infra.KitchenridersClient;

class DeliveryOrderTest {
    private DeliveryOrder delivery;

    private KitchenridersClient fakeRidersClient;

    @BeforeEach
    void setUp() {
        delivery = new DeliveryOrder(createOrderLineItems(), DeliveryAddress.of("주소"));
        fakeRidersClient = new FakeRidersClient();

    }

    @DisplayName("주문을 수락 한다.")
    @Test
    void accept1() {
        delivery.accept(fakeRidersClient);
        assertThat(delivery.isSameStatus(DeliveryOrderStatus.ACCEPTED)).isTrue();
    }

    @DisplayName("주문이 대기 상태가 아니면 수락을 할 수 없다.")
    @Test
    void accept2() {
        delivery.accept(fakeRidersClient);
        delivery.serve();
        assertThatThrownBy(() -> delivery.accept(new FakeRidersClient()))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("접수 대기 중인 주문만 접수할 수 있다.");
    }

    @DisplayName("주문을 서빙 한다.")
    @Test
    void serve1() {
        delivery.accept(fakeRidersClient);
        delivery.serve();
        assertThat(delivery.isSameStatus(DeliveryOrderStatus.SERVED)).isTrue();
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serve2() {
        assertThatThrownBy(() -> delivery.serve())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("접수된 주문만 서빙할 수 있다.");
    }

    @DisplayName("배달을 시작한다.")
    @Test
    void startDelivery1() {
        delivery.accept(fakeRidersClient);
        delivery.serve();
        delivery.startDelivery();
        assertThat(delivery.isSameStatus(DeliveryOrderStatus.DELIVERING)).isTrue();
    }

    @DisplayName("서빙된 주문만 배달을 시작 할 수 있다.")
    @Test
    void startDelivery2() {
        delivery.accept(fakeRidersClient);
        assertThatThrownBy(() -> delivery.startDelivery())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("서빙된 주문만 배달할 수 있다.");
    }

    @DisplayName("배달을 종료 한다.")
    @Test
    void completeDelivery1() {
        delivery.accept(fakeRidersClient);
        delivery.serve();
        delivery.startDelivery();
        delivery.completeDelivery();
        assertThat(delivery.isSameStatus(DeliveryOrderStatus.DELIVERED)).isTrue();
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @Test
    void completeDelivery2() {
        assertThatThrownBy(() -> delivery.completeDelivery())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("배달 중인 주문만 배달 완료할 수 있다.");
    }

    @DisplayName("주문을 종료 한다.")
    @Test
    void complete1() {
        delivery.accept(fakeRidersClient);
        delivery.serve();
        delivery.startDelivery();
        delivery.completeDelivery();
        delivery.complete();
        assertThat(delivery.isSameStatus(DeliveryOrderStatus.COMPLETED)).isTrue();
    }

    @DisplayName("배달 완료된 주문만 완료할 수 있다.")
    @Test
    void complete2() {
        assertThatThrownBy(() -> delivery.complete())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("배달 완료된 주문만 완료할 수 있다.");
    }

}
