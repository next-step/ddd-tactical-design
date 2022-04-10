package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NumberOfGuestsTest {

    @DisplayName("NumberOfGuests 를 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new NumberOfGuests(1));
    }

    @DisplayName("방문한 손님 수가 0 보다 작을 수 없다. ")
    @Test
    void invalidNumberOfGuests() {
        assertThatThrownBy(() -> new NumberOfGuests(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 방문한 손님 수 입니다.");
    }
}
