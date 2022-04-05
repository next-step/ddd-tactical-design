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
    public static final EatInOrder 대기중주문 = EatInOrderFixture.매장주문(ORDER_LINE_ITEMS, ORDER_TABLE);
    public static final EatInOrder 수락된주문 = EatInOrderFixture.수락된_매장주문(ORDER_LINE_ITEMS, ORDER_TABLE);
    public static final EatInOrder 서빙된주문 = EatInOrderFixture.서빙된_매장주문(ORDER_LINE_ITEMS, ORDER_TABLE);
    public static final EatInOrder 완료주문 = EatInOrderFixture.완료된_매장주문(ORDER_LINE_ITEMS, ORDER_TABLE);

    @DisplayName("생성할 수 있다.")
    @Test
    void create() {
        final EatInOrder order = new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE.getId());
        assertThat(order.getId()).isNotNull();
        assertThat(order).isEqualTo(EatInOrderFixture.매장주문(ORDER_LINE_ITEMS, ORDER_TABLE));
    }

    @DisplayName("수락할 수 있다.")
    @Test
    void accept() {
        final EatInOrder order = new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE.getId());
        order.accept();
        assertThat(order).isEqualTo(EatInOrderFixture.수락된_매장주문(ORDER_LINE_ITEMS, ORDER_TABLE));
    }

    @DisplayName("대기상태가 아닌 주문을 수락하면 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @MethodSource("orders_without_waiting")
    void accept_not(final EatInOrder order) {
        ThrowableAssert.ThrowingCallable throwingCallable = order::accept;
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(throwingCallable);
    }

    private static List<Arguments> orders_without_waiting() {
        return Arrays.asList(
                Arguments.of(수락된주문),
                Arguments.of(서빙된주문),
                Arguments.of(완료주문)
        );
    }

    @DisplayName("서빙할 수 있다.")
    @Test
    void serve() {
        final EatInOrder order = EatInOrderFixture.수락된_매장주문(ORDER_LINE_ITEMS, ORDER_TABLE);
        order.serve();
        assertThat(order).isEqualTo(서빙된주문);
    }

    @DisplayName("수락상태가 아닌 주문을 서빙하면 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @MethodSource("orders_without_accepted")
    void serve_not(final EatInOrder order) {
        ThrowableAssert.ThrowingCallable throwingCallable = order::serve;
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(throwingCallable);
    }

    private static List<Arguments> orders_without_accepted() {
        return Arrays.asList(
                Arguments.of(서빙된주문),
                Arguments.of(대기중주문),
                Arguments.of(완료주문)
        );
    }

    @DisplayName("완료할 수 있다.")
    @Test
    void complete() {
        final EatInOrder order = EatInOrderFixture.서빙된_매장주문(ORDER_LINE_ITEMS, ORDER_TABLE);
        order.complete();
        assertThat(order).isEqualTo(완료주문);
    }

    @DisplayName("서빙상태가 아닌 주문을 완료하면 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @MethodSource("orders_without_served")
    void complete_not(final EatInOrder order) {
        ThrowableAssert.ThrowingCallable throwingCallable = order::complete;
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(throwingCallable);
    }

    private static List<Arguments> orders_without_served() {
        return Arrays.asList(
                Arguments.of(수락된주문),
                Arguments.of(대기중주문),
                Arguments.of(완료주문)
        );
    }
}
