package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductQuantityTest {

  @DisplayName("메뉴 상품 수량은 0개 이상이어야 한다.")
  @Test
  void positiveTest() {
    //given
    long negativeValue = -1L;
    long zeroValue = 0L;
    long positiveValue = 1000L;

    //when & then
    assertAll(
        () -> assertThatThrownBy(() -> new MenuProductQuantity(negativeValue)).isInstanceOf(IllegalArgumentException.class),
        () -> assertDoesNotThrow(() -> new MenuProductQuantity(zeroValue)),
        () -> assertDoesNotThrow(() -> new MenuProductQuantity(positiveValue))
    );

  }

  @DisplayName("같은 수량인지 비교할 수 있다.")
  @Test
  void equalsTest() {
    //given
    long value = 1000L;
    MenuProductQuantity price = new MenuProductQuantity(value);
    MenuProductQuantity theOther = new MenuProductQuantity(value);

    //when & then
    assertThat(price.equals(theOther)).isTrue();
  }

}
