package kitchenpos.eatinorders.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestsTest {

    @DisplayName("손님 수 초기 생성시 0명으로 생성된다.")
    @Test
    void init_number_of_guests() {
        NumberOfGuests sut = NumberOfGuests.init();

        assertThat(sut.getNumberOfGuests()).isZero();
    }

    @DisplayName("손님 수를 0 보다 작은 값으로 변경 한다면 예외.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -1})
    void number_of_guests_under_zero(int number) {
        NumberOfGuests numberOfGuests = NumberOfGuests.init();

        assertThatThrownBy(() -> numberOfGuests.changeNumberOfGuests(number))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("손님 수를 초기화 하면 0명으로 생성된다.")
    @Test
    void clear() {
        NumberOfGuests numberOfGuests = NumberOfGuests.init();
        numberOfGuests.changeNumberOfGuests(5);

        NumberOfGuests sut = numberOfGuests.clear();

        assertThat(sut.getNumberOfGuests()).isZero();
    }
}
