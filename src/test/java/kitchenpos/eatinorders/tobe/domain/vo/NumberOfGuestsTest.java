package kitchenpos.eatinorders.tobe.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("손님수")
class NumberOfGuestsTest {

    @DisplayName("손님수 생성")
    @Test
    void createQuantity() {
        NumberOfGuests numberOfGuests = new NumberOfGuests(2);

        assertThat(numberOfGuests).isEqualTo(new NumberOfGuests(2));
    }

    @DisplayName("손님수가 0보다 작으면 에러")
    @ValueSource(ints = {-1, -10})
    @ParameterizedTest
    void quantityNegative(int value) {
        assertThatThrownBy(() -> new NumberOfGuests(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
