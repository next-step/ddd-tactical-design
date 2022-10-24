package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NumberOfGuestsTest {

    @Test
    @DisplayName("방문 고객 수를 생성한다.")
    void createNumberOfGuests() {
        // given
        int guests = 0;

        // when
        NumberOfGuests numberOfGuests = new NumberOfGuests(guests);

        // then
        assertThat(numberOfGuests).isEqualTo(new NumberOfGuests(guests));
    }

    @ParameterizedTest
    @DisplayName("방문 고객 수가 0보다 작을 경우 Exception을 발생시킨다.")
    @ValueSource(ints = {-1, -2})
    void createNumberOfGuests(int numberOfGuests) {
        // when
        // then
        assertThatThrownBy(() -> new NumberOfGuests(numberOfGuests))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
