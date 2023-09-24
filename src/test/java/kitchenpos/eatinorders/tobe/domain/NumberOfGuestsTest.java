package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberOfGuestsTest {

    @Test
    @DisplayName("손님 수는 0 이상이어야 한다")
    void lessThanOne() {
        assertThatThrownBy(() -> new NumberOfGuests(-1))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("손님 수를 생성한다")
    void constructor() {
        NumberOfGuests actual = new NumberOfGuests(0);
        assertThat(actual).isNotNull();
    }
}
