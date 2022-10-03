package kitchenpos.eatinorders.tobe.domain.vo;

import static kitchenpos.eatinorders.tobe.OrderFixtures.numberOfGuests;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberOfGuestsTest {

    @DisplayName("손님 수를 생성한다.")
    @Test
    void create() {
        NumberOfGuests numberOfGuests = NumberOfGuests.zero();

        assertThat(numberOfGuests).isEqualTo(numberOfGuests(0));
    }

    @DisplayName("손님 수는 0명 이상이다.")
    @Test
    void error() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> numberOfGuests(-1));
    }
}
