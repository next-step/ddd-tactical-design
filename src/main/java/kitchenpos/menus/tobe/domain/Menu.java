package kitchenpos.menus.tobe.domain;

import java.util.UUID;

public class Menu {

  private UUID id;

  private DisplayedName name;

  private Price price;

  private DisplayState displayState;

  private MenuGroup menuGroup;

  private MenuProducts menuProducts;

  public Menu(UUID id, DisplayedName name, Price price, DisplayState displayState,
      MenuGroup menuGroup, MenuProducts menuProducts) {
    validateMenuPrice(price, menuProducts);
    this.id = id;
    this.name = name;
    this.price = price;
    this.displayState = displayState;
    this.menuGroup = menuGroup;
    this.menuProducts = menuProducts;
  }

  private static void validateMenuPrice(Price price, MenuProducts menuProducts) {
    if (price.isGreaterThan(menuProducts.totalSum())) {
      throw new IllegalArgumentException();
    }
  }

  public UUID getId() {
    return id;
  }

  public DisplayedName getName() {
    return name;
  }

  public Price getPrice() {
    return price;
  }

  public DisplayState getDisplayState() {
    return displayState;
  }

  public MenuGroup getMenuGroup() {
    return menuGroup;
  }

  public MenuProducts getMenuProducts() {
    return menuProducts;
  }
}
