package kitchenpos.menus.tobe;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import kitchenpos.menus.tobe.application.FakeMenuPurgomalumClient;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.products.domain.Product;
import kitchenpos.products.tobe.ProductFixtures;

public class MenuFixtures {

  public static Menu menu() {
    return menu(19_000L, true, menuProduct());
  }

  public static Menu menu(final long price, final MenuProduct... menuProducts) {
    return menu(price, false, menuProducts);
  }

  public static Menu menu(
      final long price, final boolean displayed, final MenuProduct... menuProducts) {
    final Menu menu =
        Menu.create(
            "후라이드+후라이드",
            BigDecimal.valueOf(price),
            menuGroup(),
            Arrays.asList(menuProducts),
            displayed,
            new FakeMenuPurgomalumClient());
    return menu;
  }

  public static MenuGroup menuGroup() {
    return menuGroup("후라이드+후라이드");
  }

  public static MenuGroup menuGroup(final String name) {
    return MenuGroup.create(name);
  }

  public static MenuProduct menuProduct() {
    return MenuProduct.of(Optional.of(ProductFixtures.product()), 2L);
  }

  public static MenuProduct menuProduct(final Product product, final long quantity) {
    return MenuProduct.of(Optional.of(product), quantity);
  }
}
