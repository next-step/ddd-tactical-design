package kitchenpos.menus.tobe.domain;

import java.util.Collections;
import java.util.List;

public class MenuProducts {

  private final List<MenuProduct> menuProducts;

  public MenuProducts(List<MenuProduct> menuProducts) {
    this.menuProducts = menuProducts;
  }

  public Price totalSum() {
    return menuProducts.stream()
        .map(MenuProduct::amount)
        .reduce(Price.ZERO, Price::plus);
  }

  public List<MenuProduct> getMenuProducts() {
    return Collections.unmodifiableList(menuProducts);
  }
}
