package kitchenpos.eatinorders.domain.eatinorder.ordertable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderTableNameTest {
  @DisplayName("주문 테이블의 이름을 등록할 수 있다.")
  @Test
  void createOrderTableName() {
    OrderTableName actual = OrderTableName.of("udon");

    assertAll(
            () -> assertThat(actual.getName()).isEqualTo("udon")
    );
  }

  @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
  @ParameterizedTest
  @NullAndEmptySource
  void changeOrderTableNameWithNull(String name) {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> OrderTableName.of(name));
  }
}
