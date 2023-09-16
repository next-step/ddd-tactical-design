package kitchenpos.ordertables.domain;

import kitchenpos.ordertables.exception.NumberOfGuestException;
import kitchenpos.ordertables.exception.OrderTableErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("방문한 손님 수")
class NumberOfGuestTest {

    @DisplayName("[성공] 방문한 손님 수를 생성한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 100, 300})
    void create1(int input) {
        assertThatNoException()
                .isThrownBy(() -> new NumberOfGuest(input));
    }

    @DisplayName("[실패] 방문한 손님 수는 양수다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void create2(int input) {
        assertThatThrownBy(() -> new NumberOfGuest(input))
                .isInstanceOf(NumberOfGuestException.class)
                .hasMessage(OrderTableErrorCode.NUMBER_OF_GUEST_IS_GREATER_THAN_ZERO.getMessage());
    }

}
