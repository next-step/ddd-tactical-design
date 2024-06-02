package kitchenpos.eatinorders.todo.domain.ordertable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("착석한 고객의 수")
class NumberOfGuestsTest {
    @DisplayName("착석한 고객의 수를 생성한다")
    @Test
    void create() {
        NumberOfGuests actual = NumberOfGuests.from(4);

        assertThat(actual).isEqualTo(NumberOfGuests.from(4));
    }

    @DisplayName("착석한 고객의 수는 0명 이상이어야 한다.")
    @ValueSource(ints = {-1, -10, -999})
    @ParameterizedTest
    void fail_create(int number) {
        assertThatThrownBy(() -> NumberOfGuests.from(number))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
