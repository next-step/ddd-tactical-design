package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NumberOfGuestsTest {

    @DisplayName("NumberOfGuests를 생성한다.")
    @ValueSource(ints = {0, 1})
    @ParameterizedTest
    void create(int value) {

        // when
        final NumberOfGuests numberOfGuests = NumberOfGuests.of(value);

        // then
        assertAll(
                () -> assertThat(numberOfGuests).isNotNull(),
                () -> assertThat(numberOfGuests.value()).isEqualTo(value)
        );
    }

    @DisplayName("NumberOfGuests를 생성할 때 손님 수가 0 미만인 경우 예외를 던진다.")
    @Test
    void createWithNegativeValue() {
        // given
        final int value = -1;

        // when
        final Throwable thrown = catchThrowable(() -> NumberOfGuests.of(value));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("손님 수는 0 미만일 수 없습니다.");
    }

    @DisplayName("NumberOfGuests가 0인지 확인한다.")
    @Test
    void isZero() {
        // given
        final NumberOfGuests numberOfGuests = NumberOfGuests.of(0);

        // when
        final boolean result = numberOfGuests.isZero();

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("NumberOfGuests가 0이 아닌지 확인한다.")
    @Test
    void isNotZero() {
        // given
        final NumberOfGuests numberOfGuests = NumberOfGuests.of(1);

        // when
        final boolean result = numberOfGuests.isZero();

        // then
        assertThat(result).isFalse();
    }
}