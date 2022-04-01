package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuPriceTest {

  @DisplayName("메뉴 가격은 0원 이상이어야 한다.")
  @Test
  void positiveTest() {
    //given
    BigDecimal negativeValue = BigDecimal.valueOf(-1);
    BigDecimal zeroValue = BigDecimal.ZERO;
    BigDecimal positiveValue = BigDecimal.TEN;

    //when & then
    assertAll(
        () -> assertThatThrownBy(() -> new MenuPrice(negativeValue)).isInstanceOf(IllegalArgumentException.class),
        () -> assertDoesNotThrow(() -> new MenuPrice(zeroValue)),
        () -> assertDoesNotThrow(() -> new MenuPrice(positiveValue))
    );

  }

  @DisplayName("같은 가격인지 비교할 수 있다.")
  @Test
  void equalsTest() {
    //given
    BigDecimal value = BigDecimal.valueOf(1000);
    MenuPrice price = new MenuPrice(value);
    MenuPrice thOther = new MenuPrice(value);

    //when & then
    assertThat(price.equals(thOther)).isTrue();
  }

}
