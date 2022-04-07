package kitchenpos.menus.fixture;

import java.math.BigDecimal;
import kitchenpos.menus.stub.TestProductRepository;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductQuantity;

public class MenuProductFixture {

  private MenuProductFixture() {}

  public static MenuProduct buildMenuProduct(long index) {
    String testName = String.format("테스트상품_%d", index);
    BigDecimal productPrice = BigDecimal.valueOf(1000L * index);
    Long productId = index;
    MenuProductQuantity quantity = new MenuProductQuantity(index);
    return new MenuProduct(productId, quantity, new TestProductRepository(productId, testName, productPrice));
  }

}
