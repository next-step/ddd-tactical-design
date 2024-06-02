package kitchenpos.menus.tobe.domain.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kitchenpos.supports.domain.tobe.Price;

public class MenuProducts {
  private List<MenuProduct> menuProducts;

  public MenuProducts(List<MenuProduct> menuProducts) {
    validate(menuProducts);
    this.menuProducts = new ArrayList<>(menuProducts);
  }

  private void validate(List<MenuProduct> menuProducts) {
    if(Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  public Price getTotalPrice() {
    return this.menuProducts.stream()
        .map(MenuProduct::getPrice)
        .reduce(Price::add)
        .orElse(Price.ZERO);
  }
}
