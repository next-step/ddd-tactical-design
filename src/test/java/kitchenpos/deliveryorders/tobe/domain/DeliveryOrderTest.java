package kitchenpos.deliveryorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

import static kitchenpos.deliveryorders.tobe.DeliveryOrderFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryOrderTest {

    private final String deliveryAddress = "서울시 송파구 위례성대로 2";

    @Test
    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다")
    void createdMenuRequired() {
        assertThatThrownBy(() -> DeliveryOrder.create(
                List.of(menu()),
                List.of(orderLineItem(UUID.randomUUID())),
                deliveryAddress
            )
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다")
    void invalidDeliveryAddress(String deliveryAddress) {
        assertThatThrownBy(() -> DeliveryOrder.create(
                List.of(menu()),
                List.of(orderLineItem()),
                deliveryAddress
            )
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 항목의 수량은 0 이상이어야 한다")
    void lessThanZero() {
        assertThatThrownBy(() -> DeliveryOrder.create(
                List.of(menu()),
                List.of(orderLineItem(-1L)),
                deliveryAddress
            )
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("숨겨진 메뉴는 주문할 수 없다")
    void hiddenMenu() {
        assertThatThrownBy(() -> DeliveryOrder.create(
                List.of(menu(false)),
                List.of(orderLineItem()),
                deliveryAddress
            )
        ).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다")
    void equalMenuPrice() {
        assertThatThrownBy(() -> DeliveryOrder.create(
                List.of(menu(17_000L)),
                List.of(orderLineItem()),
                deliveryAddress
            )
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴를 생성한다")
    void create() {
        DeliveryOrder actual = DeliveryOrder.create(
            List.of(menu()),
            List.of(orderLineItem()),
            deliveryAddress
        );
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("접수 대기 중인 주문만 접수할 수 있다")
    void acceptFailed() {
        DeliveryOrder deliveryOrder = acceptedOrder();
        assertThatThrownBy(deliveryOrder::accept)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 접수한다")
    void accept() {
        DeliveryOrder deliveryOrder = deliveryOrder();
        deliveryOrder.accept();
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("접수된 주문만 서빙할 수 있다")
    void serveFailed() {
        DeliveryOrder deliveryOrder = servedOrder();
        assertThatThrownBy(deliveryOrder::serve)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 서빙한다")
    void serve() {
        DeliveryOrder deliveryOrder = acceptedOrder();
        deliveryOrder.serve();
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    @DisplayName("서빙된 주문만 배달할 수 있다")
    void startDeliveryFailed() {
        DeliveryOrder deliveryOrder = deliveringOrder();
        assertThatThrownBy(deliveryOrder::startDelivery)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 배달한다")
    void startDelivery() {
        DeliveryOrder deliveryOrder = servedOrder();
        deliveryOrder.startDelivery();
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @Test
    @DisplayName("배달 중인 주문만 배달할 수 있다")
    void completeDeliveryFailed() {
        DeliveryOrder deliveryOrder = deliveredOrder();
        assertThatThrownBy(deliveryOrder::completeDelivery)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 배달 완료한다")
    void completeDelivery() {
        DeliveryOrder deliveryOrder = deliveringOrder();
        deliveryOrder.completeDelivery();
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    @DisplayName("배달 완료된 주문만 완료할 수 있다")
    void completeFailed() {
        DeliveryOrder deliveryOrder = completedOrder();
        assertThatThrownBy(deliveryOrder::complete)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 완료한다")
    void complete() {
        DeliveryOrder deliveryOrder = deliveredOrder();
        deliveryOrder.complete();
        assertThat(deliveryOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}
