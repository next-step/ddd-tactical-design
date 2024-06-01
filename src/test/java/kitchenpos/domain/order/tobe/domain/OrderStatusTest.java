package kitchenpos.domain.order.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class OrderStatusTest {

    @DisplayName("주문 상태 천이를 성공한다")
    @ParameterizedTest
    @CsvSource(value = {
            "WAITING~ACCEPTED",
            "ACCEPTED~SERVED",
            "SERVED~COMPLETED",
            "SERVED~DELIVERING",
            "DELIVERING~DELIVERED",
            "DELIVERED~COMPLETED",
    }, delimiter = '~')
    void changeTo(OrderStatus before, OrderStatus after) {
        assertThat(before.changeTo(after)).isEqualTo(after);
    }

    @DisplayName("주문 상태 WAITING의 천이를 실패한다")
    @EnumSource(value = OrderStatus.class, names = {"ACCEPTED"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "주문 상태 WAITING -> {0} 천이를 실패한다")
    void waiting_changeTo_fail(OrderStatus orderStatus) {
        assertThatIllegalStateException().isThrownBy(() -> OrderStatus.WAITING.changeTo(orderStatus));
    }

    @DisplayName("주문 상태 ACCEPTED의 천이를 실패한다")
    @EnumSource(value = OrderStatus.class, names = {"SERVED"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "주문 상태 ACCEPTED -> {0} 천이를 실패한다")
    void accepted_changeTo_fail(OrderStatus target) {
        assertThatIllegalStateException().isThrownBy(() -> OrderStatus.ACCEPTED.changeTo(target));
    }

    @DisplayName("주문 상태 SERVED의 천이를 실패한다")
    @EnumSource(value = OrderStatus.class, names = {"DELIVERING", "COMPLETED"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "주문 상태 SERVED -> {0} 천이를 실패한다")
    void served_changeTo_fail(OrderStatus orderStatus) {
        assertThatIllegalStateException().isThrownBy(() -> OrderStatus.SERVED.changeTo(orderStatus));
    }

    @DisplayName("주문 상태 DELIVERING의 천이를 실패한다")
    @EnumSource(value = OrderStatus.class, names = {"DELIVERED"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "주문 상태 DELIVERING -> {0} 천이를 실패한다")
    void delivering_changeTo_fail(OrderStatus orderStatus) {
        assertThatIllegalStateException().isThrownBy(() -> OrderStatus.DELIVERING.changeTo(orderStatus));
    }

    @DisplayName("주문 상태 DELIVERED의 천이를 실패한다")
    @EnumSource(value = OrderStatus.class, names = {"COMPLETED"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "주문 상태 DELIVERED -> {0} 천이를 실패한다")
    void delivered_changeTo_fail(OrderStatus orderStatus) {
        assertThatIllegalStateException().isThrownBy(() -> OrderStatus.DELIVERED.changeTo(orderStatus));
    }

    @DisplayName("주문 상태 COMPLETED의 천이를 실패한다")
    @EnumSource(value = OrderStatus.class)
    @ParameterizedTest(name = "주문 상태 COMPLETED -> {0} 천이를 실패한다")
    void completed_changeTo_fail(OrderStatus orderStatus) {
        assertThatIllegalStateException().isThrownBy(() -> OrderStatus.COMPLETED.changeTo(orderStatus));
    }
}
