package kitchenpos.eatinorders.feedback.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NumberOfGuestTest {
    @DisplayName("NumberOfGuest를 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new NumberOfGuest(10));
    }

    @DisplayName("손님 수는 음수일 수 없다.")
    @Test
    void createWithNegative() {
        assertThatThrownBy(() -> new NumberOfGuest(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("손님 수가 같으면 같은 NumberOfGuest다.")
    @Test
    void equals() {
        assertThat(new NumberOfGuest(1)).isEqualTo(new NumberOfGuest(1));
    }

    @DisplayName("손님 수가 다르면 다른 NumberOfGuest다.")
    @Test
    void notEquals() {
        assertThat(new NumberOfGuest(1)).isNotEqualTo(new NumberOfGuest(0));
    }
}
