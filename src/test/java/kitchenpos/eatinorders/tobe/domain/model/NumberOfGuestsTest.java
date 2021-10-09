package kitchenpos.eatinorders.tobe.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberOfGuestsTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> new NumberOfGuests(1L));
    }

    @DisplayName("음수 값 생성시 에러 검증")
    @Test
    void negativeValueCreate() {
        assertThatThrownBy(() -> new NumberOfGuests(-1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("동등성 검증")
    @Test
    void equals() {
        NumberOfGuests numberOfGuests1 = new NumberOfGuests(1L);
        NumberOfGuests numberOfGuests2 = new NumberOfGuests(1L);

        assertThat(numberOfGuests1).isEqualTo(numberOfGuests2);
    }

}