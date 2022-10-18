package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestTest {

  @DisplayName("손님 수를 생성할 수 있다")
  @Test
  void create() {
    assertThat(NumberOfGuest.from(5)).isEqualTo(NumberOfGuest.from(5));
  }

  @DisplayName("손님 수가 올바르지 않으면 변경할 수 없다.")
  @ValueSource(ints = -1)
  @ParameterizedTest
  void create_NotValidNumber(final int numberOfGuests) {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> NumberOfGuest.from(numberOfGuests));
  }

}