package kitchenpos.orders.tobe.domain;

import static kitchenpos.TobeFixtures.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import kitchenpos.common.tobe.infra.TestContainer;

class TakeoutOrderTest {

    private KitchenridersClient kitchenridersClient;

    @BeforeEach
    void setUp() {
        TestContainer testContainer = new TestContainer();
        kitchenridersClient = testContainer.kitchenridersClient;
    }

    @DisplayName("테이크아웃 주문을 생성할 수 있다.")
    @Test
    void createTakeoutOrder() {
        // given & when
        final TakeoutOrder takeoutOrder = takeoutOrder(OrderStatus.WAITING);

        // then
        assertThat(takeoutOrder).isNotNull();
        assertThat(takeoutOrder.getType()).isEqualTo(OrderType.TAKEOUT);
    }

    @DisplayName("접수 대기 중인 테이크아웃 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void acceptInvalidStatusOrder(OrderStatus status) {
        // given
        final TakeoutOrder takeoutOrder = takeoutOrder(status);

        // when // then
        assertThatThrownBy(() -> takeoutOrder.accepted(kitchenridersClient))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void acceptOrder() {
        // given
        final TakeoutOrder takeoutOrder = takeoutOrder(OrderStatus.WAITING);

        // when
        takeoutOrder.accepted(kitchenridersClient);

        // then
        assertThat(takeoutOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수된 테이크아웃 주문을 서빙한다.")
    @Test
    void serveOrder() {
        // given
        final TakeoutOrder takeoutOrder = takeoutOrder(OrderStatus.ACCEPTED);

        // when
        takeoutOrder.served();

        // then
        assertThat(takeoutOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("서빙된 테이크아웃 주문을 완료한다.")
    @Test
    void completeOrder() {
        // given
        final TakeoutOrder takeoutOrder = takeoutOrder(OrderStatus.SERVED);

        // when
        takeoutOrder.completed();

        // then
        assertThat(takeoutOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}

