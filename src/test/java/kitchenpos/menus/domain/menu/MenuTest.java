package kitchenpos.menus.domain.menu;

import kitchenpos.Fixtures;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.products.infra.FakeProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MenuTest {

  private ProfanityValidator profanityValidator;
  private MenuProducts menuProducts;
  private Menu menu;

  @BeforeEach
  void setUp() {
    profanityValidator = new FakeProfanityValidator();
    menu = Fixtures.menu();
    menuProducts = MenuProducts.of(Fixtures.menuProduct());
  }

  @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
  @Test
  void registerMenuWithOneOrMoreMenuProduct(){
    final Menu actual = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, menuProducts, profanityValidator);

    assertThat(actual.getMenuPrice()).isEqualTo(BigDecimal.valueOf(20_000L));

    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, MenuProducts.of(), profanityValidator))
            .withMessageContaining("`메뉴상품`들이 한개 이상 있어야한다.");

  }
  @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
  @Test
  void registerMenuNeedsMenuGroup(){
    final MenuGroup menuGroup = menuGroup();
    final Menu actual = Menu.of("후라이드+후라이드", 20_000L, menuGroup.getId(), true, menuProducts, profanityValidator);

    assertThat(actual.getMenuGroupId()).isEqualTo(menuGroup.getId());

    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Menu.of("후라이드+후라이드", 20_000L, null, true, menuProducts, profanityValidator))
            .withMessageContaining("`메뉴는 특정 메뉴 그룹에 속해야 한다.");
  }

  @DisplayName("메뉴의 가격을 변경할 수 있다.")
  @Test
  void changeMenuPrice(){
    Menu actual = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, menuProducts, profanityValidator);

    actual.changePrice(BigDecimal.valueOf(20_000L));

    assertThat(actual.getMenuPrice()).isEqualTo(BigDecimal.valueOf(20_000L));
  }

  @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
  @Test
  void failToChangeMenuPriceWithMenuProductsSumHigher(){
    Menu actual = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, menuProducts, profanityValidator);

    assertThat(actual.getMenuPrice()).isEqualTo(BigDecimal.valueOf(20_000L));

    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> actual.changePrice(BigDecimal.valueOf(230_000L)))
            .withMessageContaining("`메뉴 상품 가격`의 총액보다 `메뉴 가격`이 클 수 없다.");
  }


  @DisplayName("메뉴를 노출할 수 있다.")
  @Test
  void displayMenu(){
    Menu actual = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, menuProducts, profanityValidator);

    actual.display();

    assertThat(actual.isDisplayed()).isTrue();
  }
  @DisplayName("메뉴를 숨길수 수 있다.")
  @Test
  void hideMenu(){
    Menu actual = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, menuProducts, profanityValidator);

    actual.hide();

    assertThat(actual.isDisplayed()).isFalse();
  }

  @DisplayName("메뉴에 속한 상품 금액의 합을 구할 수 있다.")
  @Test
  void makeSummationOfMenuProducts(){
    MenuProduct menuProduct = Fixtures.menuProduct(Fixtures.createProduct("우동 한사바리"), 2L);

    Menu menu = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, MenuProducts.of(menuProduct), profanityValidator);

    assertThat(menu.sumMenuProducts()).isEqualTo(BigDecimal.valueOf(20_000L));
  }
}
