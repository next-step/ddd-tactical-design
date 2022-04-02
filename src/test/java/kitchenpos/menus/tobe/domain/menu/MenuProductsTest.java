package kitchenpos.menus.tobe.domain.menu;

import static kitchenpos.menus.fixture.MenuProductFixture.buildMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

  @DisplayName("메뉴 상품은 1개 이상이어야한다.")
  @Test
  void minTest() {
    //given & when & then
    assertAll(
        () -> assertThatThrownBy(() -> new MenuProducts(null, null)).isInstanceOf(IllegalArgumentException.class),
        () -> assertThatThrownBy(() -> new MenuProducts(Collections.emptyList(), null)).isInstanceOf(IllegalArgumentException.class)
    );
  }

  @DisplayName("메뉴 상품 금액의 합을 구할 수 있다.")
  @Test
  void sumTest() {
    //given
    MenuProduct product1 = buildMenuProduct(1L);
    MenuProduct product2 = buildMenuProduct(2L);
    MenuProduct product3 = buildMenuProduct(3L);
    List<MenuProduct> menuProducts = Arrays.asList(product1, product2, product3);
    MenuProducts givenMenuProducts = new MenuProducts(menuProducts, null);

    //when
    MenuPrice result = givenMenuProducts.sum();

    //then
    assertThat(result).isEqualTo(calculateExpectedSum(menuProducts));
  }

  private MenuPrice calculateExpectedSum(List<MenuProduct> menuProducts) {
    BigDecimal sum = menuProducts.stream()
        .map(MenuProduct::calculateAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    return new MenuPrice(sum);
  }

}
