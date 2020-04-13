package kitchenpos.eatinorders.tobe.order.domain;

import kitchenpos.eatinorders.tobe.order.domain.exception.OrderAlreadyCompletedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @DisplayName("주문을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final Long tableId = TABLE_EMPTY_1_ID;
        final List<OrderLine> orderLines = Arrays.asList(orderLineOneFriedChicken());

        // when
        final Order order = new Order(tableId, orderLines);

        // then
        assertThat(order.getTableId()).isEqualTo(tableId);
        assertThat(order.getOrderLines()).containsExactlyInAnyOrderElementsOf(orderLines);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COOKING);
    }

    @DisplayName("주문 생성 시, 테이블을 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1, 0})
    void createFailsWhenTableNotEntered(final Long tableId) {
        // given
        final List<OrderLine> orderLines = Arrays.asList(orderLineOneFriedChicken());

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(tableId, orderLines);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 상태를 변경할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideOrderStatus")
    void changeStatus(final OrderStatus status) {
        // given
        final Order order = orderCooking();

        // when
        order.changeStatus(status);

        // then
        assertThat(order.getStatus()).isEqualTo(status);
    }

    private static Stream provideOrderStatus() {
        return Stream.of(
                OrderStatus.COOKING,
                OrderStatus.MEAL,
                OrderStatus.COMPLETION
        );
    }

    @DisplayName("주문 상태를 변경 시, 이미 완료된 주문이면 안된다.")
    @ParameterizedTest
    @MethodSource(value = "provideOrderStatus")
    void changeStatusFailsWhenAlreadyCompleted(final OrderStatus status) {
        // given
        final Order order = orderCompleted();

        // when
        // then
        assertThatThrownBy(() -> {
            order.changeStatus(status);
        }).isInstanceOf(OrderAlreadyCompletedException.class);
    }
}
