package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestsTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 손님_수는_0명_이상이다(int numberOfGuests) {
        assertDoesNotThrow(() -> new NumberOfGuests(numberOfGuests));
    }

    @Test
    void 손님_수는_음수일_수_없다() {
        assertThatThrownBy(() -> new NumberOfGuests(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("손님 수는 0명 이상이어야 합니다. 현재 값: -1");
    }

    @Test
    void NumberOfGuests_동등성_비교() {
        NumberOfGuests actual = new NumberOfGuests(0);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals("wrong class")).isFalse();

        assertThat(actual.equals(new NumberOfGuests(0))).isTrue();
        assertThat(actual.equals(new NumberOfGuests(1))).isFalse();
    }

    @Test
    void NumberOfGuests_hashCode() {
        NumberOfGuests actual = new NumberOfGuests(0);

        assertThat(actual.hashCode()).isEqualTo(new NumberOfGuests(0).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new NumberOfGuests(1).hashCode());
    }
}
