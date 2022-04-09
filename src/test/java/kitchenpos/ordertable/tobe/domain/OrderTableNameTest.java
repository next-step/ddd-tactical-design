package kitchenpos.ordertable.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

  @DisplayName("이름은 공백일 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void emptyNameTest(String given) {

    //when & then
    assertThatThrownBy(() -> new OrderTableName(given))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("같은 이름인지 비교할 수 있다.")
  @Test
  void equalsTest() {
    //given
    String given = "같은이름";
    OrderTableName name = new OrderTableName(given);
    OrderTableName theOther = new OrderTableName(given);

    //when & then
    assertThat(name.equals(theOther)).isTrue();
  }

}
