package kitchenpos.eatinorders.tobe.domain.ordertable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTableNumberOfGuestsTest {

  @DisplayName("주문테이블의 인원을 생성할 수 있다.")
  @Test
  void create() {
    int expected = 5;
    OrderTableNumberOfGuests actual = OrderTableFixture.createOrderTableNumberOfGuests(
        expected);
    assertThat(actual.getNumberOfGuests()).isEqualTo(expected);
  }

  @DisplayName("주문테이블의 인원은 0명 이상이어야 한다.")
  @ValueSource(ints = {-1, -5})
  @ParameterizedTest
  void create(int expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> OrderTableFixture.createOrderTableNumberOfGuests(expected));
  }
}
