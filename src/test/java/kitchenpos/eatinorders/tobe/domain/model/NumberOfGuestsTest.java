package kitchenpos.eatinorders.tobe.domain.model;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.constraints.PositiveOrZero;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberOfGuestsTest {

    @DisplayName("방문한 손님 수를 생성한다.")
    @Test
    @PositiveOrZero
    void 생성_성공(final long value) {
        final NumberOfGuests numberOfGuests = new NumberOfGuests(value);

        assertThat(numberOfGuests.value()).isEqualTo(value);
    }

    @DisplayName("방문한 손님 수는 0 미만의 방문한 손님 수 값을 가질 수 없다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L})
    void 생성_실패(final long value) {
        final ThrowableAssert.ThrowingCallable when = () -> new NumberOfGuests(value);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("방문한 손님 수는 0 이상이어야 합니다.");
    }
}
