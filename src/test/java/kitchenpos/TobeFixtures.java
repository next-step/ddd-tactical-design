package kitchenpos;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.DisplayState;
import kitchenpos.menus.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.Quantity;
import kitchenpos.products.tobe.domain.DisplayNameValidator;

public class TobeFixtures {

  public static final UUID INVALID_ID = new UUID(0L, 0L);

  public static MenuGroup createMenuGroup(String name) {
    return new MenuGroup(UUID.randomUUID(), DisplayedName.from(name));
  }

  public static Menu createMenu(
      String name,
      long price,
      boolean displayed,
      MenuGroup menuGroup,
      List<MenuProduct> menuProducts,
      DisplayNameValidator displayNameValidator
  ) {
    return new Menu(
        UUID.randomUUID(),
        DisplayedName.from(name, displayNameValidator),
        Price.from(price),
        DisplayState.from(displayed),
        menuGroup,
        new MenuProducts(menuProducts)
    );
  }

  public static MenuProduct createMenuProduct(
      UUID productId,
      long price,
      long quantity
  ) {
    return new MenuProduct(productId, Price.from(price), Quantity.from(quantity));
  }

}
