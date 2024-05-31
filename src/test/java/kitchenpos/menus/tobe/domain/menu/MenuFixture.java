package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;

public class MenuFixture {
  public static MenuPrice normalMenuPrice() {
    return createMenuPrice(new BigDecimal(500));
  }

  public static MenuPrice createMenuPrice(BigDecimal price) {
    return new MenuPrice(price);
  }
}
