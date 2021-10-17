package kitchenpos.eatinorders.tobe.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestsTest {

    @DisplayName("0 이상의 수로 NumberOfGuests를 생성할 수 있다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, Integer.MAX_VALUE})
    void ok(final int value) {
        final NumberOfGuests numberOfGuests = new NumberOfGuests(value);
        assertThat(numberOfGuests).isNotNull();
    }

    @DisplayName("음수로 NumberOfGuests를 생성하면 Exception이 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, -1000, Integer.MIN_VALUE})
    void nagative(final int value) {
        assertThatThrownBy(() -> new NumberOfGuests(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
