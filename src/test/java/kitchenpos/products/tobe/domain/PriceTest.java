package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

  @DisplayName("가격은 0원 이상이어야 한다.")
  @Test
  void positiveTest() {
    //given
    BigDecimal negativeValue = BigDecimal.valueOf(-1);

    //when & then
    assertThatThrownBy(() -> new Price(negativeValue))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("같은 가격인지 비교할 수 있다.")
  @Test
  void equalsTest() {
    //given
    BigDecimal value = BigDecimal.valueOf(1000);
    Price price = new Price(value);
    Price thOther = new Price(value);

    //when & then
    assertThat(price.equals(thOther)).isTrue();
  }

}
