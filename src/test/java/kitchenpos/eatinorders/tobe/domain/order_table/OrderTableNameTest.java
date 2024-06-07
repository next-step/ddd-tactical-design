package kitchenpos.eatinorders.tobe.domain.order_table;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

  @DisplayName("주문테이블의 이름을 등록할 수 있다.")
  @Test
  void create() {
    String expected = "주문테이블명";
    OrderTableName actual = OrderTableFixture.createOrderTableName(expected);
    assertThat(actual.getName()).isEqualTo(expected);
  }

  @DisplayName("주문테이블의 이름은 비워 둘 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create(String expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> OrderTableFixture.createOrderTableName(expected));
  }
}
