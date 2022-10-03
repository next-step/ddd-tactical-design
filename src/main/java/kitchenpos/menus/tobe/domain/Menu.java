package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  private DisplayedName name;

  @Embedded
  private Price price;

  @Embedded
  private DisplayState displayState;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "menu_group_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
  )
  private MenuGroup menuGroup;

  private MenuProducts menuProducts;

  protected Menu() {

  }

  public Menu(UUID id, DisplayedName name, Price price, DisplayState displayState,
      MenuGroup menuGroup, MenuProducts menuProducts) {
    validateMenuGroup(menuGroup);
    validateMenuPrice(price, menuProducts);
    this.id = id;
    this.name = name;
    this.price = price;
    this.displayState = displayState;
    this.menuGroup = menuGroup;
    this.menuProducts = menuProducts;
  }

  public void changePrice(Price price) {
    validateMenuPrice(price, menuProducts);
    this.price = price;
  }

  public void show() {
    this.displayState = DisplayState.show();
  }
  public void hide() {
    this.displayState = DisplayState.hide();
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

  private static void validateMenuGroup(MenuGroup menuGroup) {
    if (Objects.isNull(menuGroup)) {
      throw new IllegalArgumentException();
    }
  }

  private static void validateMenuPrice(Price price, MenuProducts menuProducts) {
    if (price.isGreaterThan(menuProducts.totalSum())) {
      throw new IllegalArgumentException();
    }
  }

}
