package kitchenpos.menus.tobe;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.application.FakeMenuPurgomalumClient;
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
    final Product product = ProductFixtures.product();

    return MenuProduct.of(product.getId(), 2L, product.getPrice());
  }

  public static MenuProduct menuProduct(final long quantity) {
    final Product product = ProductFixtures.product();

    return MenuProduct.of(product.getId(), quantity, product.getPrice());
  }

  public static MenuProduct menuProduct(final Product product, final long quantity) {
    return MenuProduct.of(product.getId(), quantity, product.getPrice());
  }
}
