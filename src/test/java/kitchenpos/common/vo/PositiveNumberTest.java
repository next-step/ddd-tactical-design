package kitchenpos.common.vo;

import kitchenpos.common.exception.NegativeNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositiveNumberTest {

    @DisplayName("음수는 예외를 발생시킨다")
    @ValueSource(ints = {-1})
    @ParameterizedTest
    void validate(int number) {
        assertThatThrownBy(() -> new PositiveNumber(number))
                .isInstanceOf(NegativeNumberException.class);
    }
}