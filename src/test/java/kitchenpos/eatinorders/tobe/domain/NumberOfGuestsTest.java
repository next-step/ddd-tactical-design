package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class NumberOfGuestsTest {

    @DisplayName("방문한 손님 수를 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new NumberOfGuests(1))
                .doesNotThrowAnyException();
    }

    @DisplayName("방문한 손님 수는 0 이상이어야 한다")
    @Test
    void createInvalidNumberOfGuests() {
        assertThatThrownBy(() -> new NumberOfGuests(-1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("방문한 손님 수는 0 이상이어야 합니다. 입력값 : " + -1);
    }
}
