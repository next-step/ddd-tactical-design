package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.application.FakeKitchenridersClient;
import kitchenpos.eatinorders.application.tobe.OrderFixtures;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TobeOrderTest extends OrderFixtures {
    private FakeKitchenridersClient kitchenridersClient;

    @BeforeEach
    void setUp() {
        kitchenridersClient = new FakeKitchenridersClient();
    }

    @Test
    void 주문_생성() {
        TobeOrder order = createOrder();

        assertThat(order).isNotNull();
    }

    @Test
    void 주문타입_예외_확인() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TobeOrder(null, createOrderLineItems(), "서울", UUID.randomUUID()));
    }

    @Test
    void 식사_테이블_예외_확인() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TobeOrder(OrderType.EAT_IN, createOrderLineItems(), null, null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 배달주문_배송지_예외_확인(String address) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TobeOrder(OrderType.DELIVERY, createOrderLineItems(), address, UUID.randomUUID()));
    }

    @Test
    void 주문대기_처리() {
        TobeOrder order = createOrder();
        order.accept(kitchenridersClient);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    void 주문대기_예외처리() {
        TobeOrder order = createOrder();
        order.accept(kitchenridersClient);
        assertThatIllegalStateException().isThrownBy(() -> order.accept(kitchenridersClient));
    }

    @Test
    void 주문승인_처리() {
        TobeOrder order = createOrder();
        order.accept(kitchenridersClient);
        order.server();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    void 주문승인_예외처리() {
        TobeOrder order = createOrder();
        assertThatIllegalStateException().isThrownBy(() -> order.server());
    }

    @Test
    void 주문_배송_처리() {
        TobeOrder order = createOrder(OrderType.DELIVERY);
        order.accept(kitchenridersClient);
        order.server();
        order.startDelivery();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @Test
    void 주문_배송_예외처리() {
        TobeOrder order = createOrder(OrderType.DELIVERY);
        assertThatIllegalStateException().isThrownBy(() -> order.startDelivery());
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 주문_배송지_예외처리(String address) {
        assertThatIllegalArgumentException().isThrownBy(() -> createOrder(OrderType.DELIVERY, address, createOrderLineItems()));
    }

    @Test
    void 주문_배송_완료_처리() {
        TobeOrder order = createOrder(OrderType.DELIVERY);
        order.accept(kitchenridersClient);
        order.server();
        order.startDelivery();
        order.completeDelivery();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    void 주문_배송_완료_예외처리() {
        TobeOrder order = createOrder(OrderType.DELIVERY);
        assertThatIllegalStateException().isThrownBy(() -> order.completeDelivery());
    }
}
