package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.application.FakeKitchenridersClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderFixture.createDeliveryOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
- 배달 주문을 등록한다.
    - [x] 1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.
    - [x] 배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.
    - [x] 배달 주소는 비워 둘 수 없다.
    - [x] 배달 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.
- 배달 주문을 시작한다.
    - [x] 배달 주문만 배달 할 수 있다.
    - [x] 서빙된 주문만 배달 할 수 있다.
- 주문을 배달 완료한다.
    - [x] 배달 중인 주문만 배달 완료할 수 있다.
    - [x] 배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.
 */
class DeliveryOrderTest {
    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void register() {
        assertThatCode(() -> createDeliveryOrder())
                .doesNotThrowAnyException();
    }

    @DisplayName("배달 주문을 시작한다.")
    @Test
    void startDelivery() {
        final Order deliveryOrder = createDeliveryOrder();
        deliveryOrder.accept(new FakeKitchenridersClient());
        deliveryOrder.serve();
        deliveryOrder.startDelivery();

        assertThat(deliveryOrder.getOrderStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final Order deliveryOrder = createDeliveryOrder();
        deliveryOrder.accept(new FakeKitchenridersClient());
        deliveryOrder.serve();
        deliveryOrder.startDelivery();

        deliveryOrder.completeDelivery();

        assertThat(deliveryOrder.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
    }
}
