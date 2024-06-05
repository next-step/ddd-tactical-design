package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import kitchenpos.menus.tobe.domain.menu_group.MenuGroupFixture;
import kitchenpos.menus.tobe.domain.menu_group.MenuGroupName;
import kitchenpos.supports.domain.tobe.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuProductsTest {

  @DisplayName("메뉴상품 목록을 생성할 수 있다.")
  @Test
  void create() {
    int size = 3;
    List<MenuProduct> menuProductList = MenuFixture.normalMenuProductList(size);
    MenuProducts menuProducts = MenuFixture.createMenuProducts(menuProductList);
    assertThat(menuProducts).isNotNull();
  }

  @DisplayName("메뉴상품 가격의 합을 계산할 수 있다.")
  @Test
  void sumPrice() {
    List<MenuProduct> menuProductList = MenuFixture.normalMenuProductList(3);
    Price expected = menuProductList.stream()
        .map(MenuProduct::getPrice)
        .reduce(Price::add)
        .orElse(Price.ZERO);
    MenuProducts menuProducts = MenuFixture.createMenuProducts(menuProductList);
    assertThat(menuProducts.getTotalPrice()).isEqualTo(expected);
  }

  @DisplayName("1개 이상의 메뉴상품을 등록해야한다.")
  @NullAndEmptySource
  @ParameterizedTest
  void fail(List<MenuProduct> menuProducts) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> MenuFixture.createMenuProducts(menuProducts));
  }
}
