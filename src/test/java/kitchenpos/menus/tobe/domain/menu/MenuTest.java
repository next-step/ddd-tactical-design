package kitchenpos.menus.tobe.domain.menu;

import static kitchenpos.menus.fixture.MenuFixture.buildEmptyMenuGroupMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildEmptyMenuProductsMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildMenuProductsAmountSumIsCheaperThanMenuPriceMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildProfanityNameMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildValidHiddenMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildValidMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildZeroPriceMenu;
import static kitchenpos.menus.fixture.MenuFixture.buildZeroQuantityMenuProductsMenu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {


  @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다")
  @Test
  void minMenuProductsTest() {
    //given & when & then
    assertThatThrownBy(() -> buildEmptyMenuProductsMenu()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
  void validQuantityTest() {
    //given & when & then
    assertThatThrownBy(() -> buildZeroQuantityMenuProductsMenu()).isInstanceOf(IllegalArgumentException.class);

  }

  @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
  void validPriceTest() {
    //given & when & then
    assertThatThrownBy(() -> buildZeroPriceMenu()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다")
  @Test
  void menuProductsAmountSumTest() {
    assertThatThrownBy(() -> buildMenuProductsAmountSumIsCheaperThanMenuPriceMenu()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
  @Test
  void emptyMenuGroupTest() {
    assertThatThrownBy(() -> buildEmptyMenuGroupMenu()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
  @Test
  void profanityNameTest() {
    assertThatThrownBy(() -> buildProfanityNameMenu()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴의 가격을 변경할 수 있다.")
  @Test
  void changePriceTest() {
    //given
    Menu menu = buildValidMenu();
    MenuPrice changePrice = new MenuPrice(BigDecimal.valueOf(500L));

    //when
    Menu result = menu.changePrice(changePrice);

    //then
    assertThat(result.getPriceValue()).isEqualTo(changePrice.value());
  }

  @DisplayName("메뉴에 속한 상품 금액의 합보다 크게 메뉴 가격을 변경할 수 없다.")
  @Test
  void changePriceFailTest() {
    Menu menu = buildValidMenu();
    MenuPrice invalidPrice = new MenuPrice(BigDecimal.valueOf(100000000L));

    //when & then
    assertThatThrownBy(() -> menu.changePrice(invalidPrice)).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴를 노출할 수 있다.")
  @Test
  void showTest() {
    //given
    Menu hiddenMenu = buildValidHiddenMenu();

    //when
    Menu result = hiddenMenu.show();

    //then
    assertThat(result.isDisplayed()).isTrue();
  }

  @DisplayName("메뉴를 숨길 수 있다.")
  @Test
  void hideTest() {
    //given
    Menu validMenu = buildValidMenu();

    //when
    Menu result = validMenu.hide();

    //then
    assertThat(result.isDisplayed()).isFalse();
  }


}
