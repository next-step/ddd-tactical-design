package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.NumberOfGuests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NUMBER_GUESTS_NEGATIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@DisplayName("고객수 테스트")
class NumberOfGuestsTest {

    @DisplayName("고객수가 음수면 예외를 반환한다.")
    @ValueSource(ints = {-3,-2,-1})
    @ParameterizedTest
    void create_failed(Integer input) {
        assertThatThrownBy( () -> NumberOfGuests.create(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NUMBER_GUESTS_NEGATIVE);
    }

    @DisplayName("고객수 생성 성공")
    @Test
    void create() {
        NumberOfGuests numberOfGuests = NumberOfGuests.create(10);
        assertThat(numberOfGuests).isEqualTo(NumberOfGuests.create(10));
    }

}