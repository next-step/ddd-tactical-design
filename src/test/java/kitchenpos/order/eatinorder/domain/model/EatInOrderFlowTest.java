package kitchenpos.order.eatinorder.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class EatInOrderFlowTest {

    private static Stream<Arguments> provideOrderStatusTransitions() {
        return Stream.of(
                Arguments.of(EatInOrderFlow.WAITING, EatInOrderStatus.ACCEPTED, true),
                Arguments.of(EatInOrderFlow.ACCEPTED, EatInOrderStatus.SERVED, true),
                Arguments.of(EatInOrderFlow.SERVED, EatInOrderStatus.COMPLETED, true),

                Arguments.of(EatInOrderFlow.WAITING, EatInOrderStatus.SERVED, false),
                Arguments.of(EatInOrderFlow.WAITING, EatInOrderStatus.COMPLETED, false),
                Arguments.of(EatInOrderFlow.ACCEPTED, EatInOrderStatus.COMPLETED, false),
                Arguments.of(EatInOrderFlow.SERVED, EatInOrderStatus.ACCEPTED, false)
        );
    }

    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = {"ACCEPTED", "SERVED", "COMPLETED"})
    @DisplayName("주문 상태에 해당하는 매장 주문 순서를 찾는다")
    void find_eat_in_order_flow(EatInOrderStatus orderStatus) {
        // when
        EatInOrderFlow result = EatInOrderFlow.findByOrderStatus(orderStatus);

        // then
        assertThat(result.name()).isEqualTo(orderStatus.name());
    }

    @ParameterizedTest
    @MethodSource("provideOrderStatusTransitions")
    @DisplayName("매장 주문 상태 변경이 올바른 흐름인지 검증한다")
    void validate_eat_in_order_status(EatInOrderFlow currentFlow, EatInOrderStatus nextStatus, boolean expected) {
        // when
        boolean result = currentFlow.validateOrderStatus(nextStatus);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
