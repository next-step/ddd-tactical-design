package kitchenpos.menus.domain.menu;

import kitchenpos.Fixtures;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.MenuProductRequest;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroupRepository;
import kitchenpos.menus.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import kitchenpos.products.domain.tobe.ProfanityValidator;
import kitchenpos.products.infra.FakeProfanityValidator;
import kitchenpos.products.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MenuTest {

  private ProfanityValidator profanityValidator;
  private MenuProducts menuProducts;

  @BeforeEach
  void setUp() {
    profanityValidator = new FakeProfanityValidator();

    menuProducts = MenuProducts.of();
    menuProducts.add(Fixtures.menuProduct());
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
}
