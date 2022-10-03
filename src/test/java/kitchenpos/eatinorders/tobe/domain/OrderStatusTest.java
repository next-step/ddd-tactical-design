package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class OrderStatusTest {

    @DisplayName("접수 상태 변경")
    @Nested
    class AcceptTest {

        @DisplayName("접수 상태로 변경한다.")
        @Test
        void success() {
            OrderStatus actual = OrderStatus.WAITING;

            assertThat(actual.accept())
                    .isEqualTo(OrderStatus.ACCEPTED);
        }

        @DisplayName("대기 상태만 변경할 수 있다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
        void error(OrderStatus status) {
            assertThatThrownBy(status::accept)
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("서빙 상태 변경")
    @Nested
    class ServeTest {

        @DisplayName("서빙 상태로 변경한다.")
        @Test
        void success() {
            OrderStatus actual = OrderStatus.ACCEPTED;

            assertThat(actual.serve())
                    .isEqualTo(OrderStatus.SERVED);
        }

        @DisplayName("접수 상태만 변경할 수 있다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
        void error(OrderStatus status) {
            assertThatThrownBy(status::serve)
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("주문 완료 상태 변경")
    @Nested
    class CompleteTest {

        @DisplayName("주문 완료 상태로 변경한다.")
        @Test
        void success() {
            OrderStatus actual = OrderStatus.SERVED;

            assertThat(actual.complete())
                    .isEqualTo(OrderStatus.COMPLETED);
        }

        @DisplayName("서빙 상태만 변경할 수 있다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
        void error(OrderStatus status) {
            assertThatThrownBy(status::complete)
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}