package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.fixture.EatInOrderFixture;
import kitchenpos.fixture.OrderTableFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("매장 주문(EatInOrder)은")
class EatInOrderTest {
    private static final List<OrderLineItem> ORDER_LINE_ITEMS = Arrays.asList(EatInOrderFixture.주문상품(), EatInOrderFixture.주문상품());
    private static final OrderTable ORDER_TABLE = OrderTableFixture.앉은테이블();

    @DisplayName("생성할 수 있다.")
    @Test
    void create() {
        final EatInOrder order = new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE);
        assertThat(order.getId()).isNotNull();
        assertThat(order).isEqualTo(new EatInOrder(ORDER_LINE_ITEMS, OrderStatus.WAITING, ORDER_TABLE));
    }

    @DisplayName("수락할 수 있다.")
    @Test
    void accept() {
        final EatInOrder order = new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE);
        order.accept();
        assertThat(order).isEqualTo(new EatInOrder(ORDER_LINE_ITEMS, OrderStatus.ACCEPTED, ORDER_TABLE));
    }

    @DisplayName("대기상태가 아닌 주문을 수락하면 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @MethodSource("status")
    void accept_notWaiting(final OrderStatus status) {
        final EatInOrder order = new EatInOrder(ORDER_LINE_ITEMS, status, ORDER_TABLE);
        ThrowableAssert.ThrowingCallable throwingCallable = order::accept;
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(throwingCallable);
    }

    private static List<Arguments> status() {
        return Arrays.asList(
                Arguments.of(OrderStatus.ACCEPTED),
                Arguments.of(OrderStatus.SERVED),
                Arguments.of(OrderStatus.COMPLETED)
        );
    }
}
