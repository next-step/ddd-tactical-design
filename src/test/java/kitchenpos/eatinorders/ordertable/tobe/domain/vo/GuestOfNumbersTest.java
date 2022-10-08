package kitchenpos.eatinorders.ordertable.tobe.domain.vo;

import kitchenpos.eatinorders.ordertable.tobe.domain.vo.exception.MinimumGuestOfNumbersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class GuestOfNumbersTest {

    @DisplayName("손님 수를 생성한다.")
    @Test
    void valueOf() {
        final GuestOfNumbers guestOfNumbers = GuestOfNumbers.valueOf(1);

        assertAll(
                () -> assertThat(guestOfNumbers.value()).isEqualTo(1),
                () -> assertThat(guestOfNumbers).isEqualTo(GuestOfNumbers.valueOf(1))
        );
    }

    @DisplayName("손님 수는 0 명보다 적을 수 없다.")
    @Test
    void invalid_guest_of_numbers() {
        assertThatThrownBy(() -> GuestOfNumbers.valueOf(-1))
                .isInstanceOf(MinimumGuestOfNumbersException.class);
    }
}
