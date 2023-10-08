package kitchenpos.order.tobe.eatinorder.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestsTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testInit(int numberOfGuests) {
        // when // then
        assertDoesNotThrow(() -> NumberOfGuests.of(numberOfGuests));
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, -3, -2, -1})
    void testInitIfNotValidValue(int numberOfGuests) {
        // when // then
        assertThatThrownBy(() -> NumberOfGuests.of(numberOfGuests))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
