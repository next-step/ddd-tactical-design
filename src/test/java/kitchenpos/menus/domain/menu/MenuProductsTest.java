package kitchenpos.menus.domain.menu;

import kitchenpos.Fixtures;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuProductsTest {

  @DisplayName("메뉴에 속한 상품 금액의 합을 구할 수 있다.")
  @Test
  void makeSummationOfMenuProducts(){
    MenuProduct menuProduct = Fixtures.menuProduct(Fixtures.createProduct("우동 한사바리"), 2L);
    MenuProducts actual = MenuProducts.of();

    actual.add(menuProduct);

    assertThat(actual.sum()).isEqualTo(BigDecimal.valueOf(20_000L));
  }
}
