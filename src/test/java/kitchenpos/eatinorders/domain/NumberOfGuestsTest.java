package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberOfGuestsTest {

    @DisplayName("손님 수는 음수일 수 없다.")
    @Test
    void negativeException() {
        assertThatThrownBy(() -> new NumberOfGuests(-1))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("손님 수는 0 보다 작을 수 없습니다.");
    }
}
