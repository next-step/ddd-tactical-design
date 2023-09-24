package kitchenpos.takeoutorders.tobe.domain;

import kitchenpos.sharedkernel.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static kitchenpos.takeoutorders.tobe.TakeOutOrderFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TakeOutOrderTest {

    @Test
    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다")
    void createdMenuRequired() {
        assertThatThrownBy(() -> TakeOutOrder.create(
                List.of(menu()),
                List.of(orderLineItem(UUID.randomUUID()))
            )
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포장 주문은 주문 항목의 수량이 0 미만일 수 있다")
    void lessThanZero() {
        TakeOutOrder actual = TakeOutOrder.create(
            List.of(menu()),
            List.of(orderLineItem(-1L))
        );
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("숨겨진 메뉴는 주문할 수 없다")
    void hiddenMenu() {
        assertThatThrownBy(() -> TakeOutOrder.create(
                List.of(menu(false)),
                List.of(orderLineItem())
            )
        ).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다")
    void equalMenuPrice() {
        assertThatThrownBy(() -> TakeOutOrder.create(
                List.of(menu(17_000L)),
                List.of(orderLineItem())
            )
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴를 생성한다")
    void create() {
        TakeOutOrder actual = TakeOutOrder.create(
            List.of(menu()),
            List.of(orderLineItem())
        );
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("접수 대기 중인 주문만 접수할 수 있다")
    void acceptFailed() {
        TakeOutOrder takeOutOrder = acceptedOrder();
        assertThatThrownBy(takeOutOrder::accept)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 접수한다")
    void accept() {
        TakeOutOrder takeOutOrder = takeOutOrder();
        takeOutOrder.accept();
        assertThat(takeOutOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("접수된 주문만 서빙할 수 있다")
    void serveFailed() {
        TakeOutOrder takeOutOrder = servedOrder();
        assertThatThrownBy(takeOutOrder::serve)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 서빙한다")
    void serve() {
        TakeOutOrder takeOutOrder = acceptedOrder();
        takeOutOrder.serve();
        assertThat(takeOutOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    @DisplayName("서빙 완료된 주문만 완료할 수 있다")
    void completeFailed() {
        TakeOutOrder takeOutOrder = completedOrder();
        assertThatThrownBy(takeOutOrder::complete)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 완료한다")
    void complete() {
        TakeOutOrder takeOutOrder = servedOrder();
        takeOutOrder.complete();
        assertThat(takeOutOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}
