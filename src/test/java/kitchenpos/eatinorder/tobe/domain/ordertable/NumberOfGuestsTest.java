package kitchenpos.eatinorder.tobe.domain.ordertable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberOfGuestsTest {

    @DisplayName("고객수가 동일한 경우 동일하다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void success(int input) {
        final var name1 = NumberOfGuests.of(input);
        final var name2 = NumberOfGuests.of(input);

        assertThat(name1).isEqualTo(name2);
    }

    @DisplayName("손님 수는 0보다 작을 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    void fail(int input) {

        assertThrows(IllegalArgumentException.class, () -> NumberOfGuests.of(input));
    }
}
