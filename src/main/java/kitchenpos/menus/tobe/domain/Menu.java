package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.UUID;

public class Menu {

  private UUID id;

  private DisplayedName name;

  private Price price;

  private DisplayState displayState;

  private MenuGroup menuGroup;

  private List<MenuProduct> menuProducts;

  public Menu(UUID id, DisplayedName name, Price price, DisplayState displayState,
      MenuGroup menuGroup, List<MenuProduct> menuProducts) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.displayState = displayState;
    this.menuGroup = menuGroup;
    this.menuProducts = menuProducts;
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

  public List<MenuProduct> getMenuProducts() {
    return menuProducts;
  }
}
