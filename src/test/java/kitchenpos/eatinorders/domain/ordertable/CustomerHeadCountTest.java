package kitchenpos.eatinorders.domain.ordertable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CustomerHeadCountTest {
  @DisplayName("고객 인원을 생성할 수 있다.")
  @Test
  void createCustomerHeadCount() {
    CustomerHeadcount actual = CustomerHeadcount.of(21);

    assertAll(
            () -> assertThat(actual.getHeadCounts()).isEqualTo(21)
    );
  }

  @DisplayName("고객 인원은 0명 이상이어야 한다.")
  @ParameterizedTest
  @ValueSource(ints = {-10_000, -1})
  void changeCustomerHeadCountWithNegativePrice(int headCounts) {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> CustomerHeadcount.of(headCounts))
            .withMessageContaining("방문한 손님 수는 0 이상이어야 한다.");
  }

  @DisplayName("고객 인원이 올바르지 않으면 변경할 수 없다.")
  @ParameterizedTest
  @NullSource
  void changeCustomerHeadCountWithNullPrice(Integer headCounts) {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> CustomerHeadcount.of(headCounts))
            .withMessageContaining("방문한 손님 수가 올바르지 않으면 변경할 수 없다.");
  }
}
