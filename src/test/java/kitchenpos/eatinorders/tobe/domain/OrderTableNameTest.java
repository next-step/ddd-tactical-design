package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

  @DisplayName("주문 테이블 아름을 생성할 수 있다")
  @Test
  void create() {
    assertThat(OrderTableName.from("9번")).isEqualTo(OrderTableName.from("9번"));
  }

  @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create_NotValidNumber(final String name) {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> OrderTableName.from(name));
  }

}