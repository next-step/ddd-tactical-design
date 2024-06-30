package kitchenpos.menus.domain.menu;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import kitchenpos.menus.domain.menugroup.MenuGroup;

@Table(name = "menu")
@Entity
public class Menu {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded private MenuName name;

  @Embedded private MenuPrice price;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "menu_group_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_to_menu_group"))
  private MenuGroup menuGroup;

  @Embedded private MenuDisplayed displayed;

  @Embedded private MenuProducts menuProducts;

  protected Menu() {}

  protected Menu(
      final String menuName,
      final BigDecimal menuPrice,
      final MenuGroup menuGroup,
      final List<MenuProduct> menuProducts,
      final boolean menuDisplayed,
      final MenuPurgomalumClient menuPurgomalumClient) {

    if (Objects.isNull(menuGroup)) {
      throw new NoSuchElementException("메뉴 그룹이 없습니다.");
    }

    this.id = UUID.randomUUID();
    this.name = new MenuName(menuName, menuPurgomalumClient);
    this.price = new MenuPrice(menuPrice);
    this.menuGroup = menuGroup;
    this.displayed = new MenuDisplayed(menuDisplayed);
    this.menuProducts = new MenuProducts(menuProducts, this.price);
  }

  public static Menu create(
      final String menuName,
      final BigDecimal menuPrice,
      final MenuGroup menuGroup,
      final List<MenuProduct> menuProducts,
      final boolean menuDisplayed,
      final MenuPurgomalumClient menuPurgomalumClient) {

    return new Menu(
        menuName, menuPrice, menuGroup, menuProducts, menuDisplayed, menuPurgomalumClient);
  }

  public void changePrice(BigDecimal menuPrice) {
    final BigDecimal sum = this.menuProducts.getTotalPrice();

    if (Objects.isNull(menuPrice) || menuPrice.compareTo(sum) > 0) {
      throw new IllegalArgumentException();
    }

    this.price = new MenuPrice(menuPrice);
  }

  public void displayed() {
    final BigDecimal sum = this.menuProducts.getTotalPrice();

    if (this.price.getPrice().compareTo(sum) > 0) {
      throw new IllegalStateException();
    }

    this.displayed = new MenuDisplayed(true);
  }

  public void hide() {
    this.displayed = new MenuDisplayed(false);
  }

  public void productPriceChanges(final BigDecimal productPrice) {
    this.menuProducts.changeProductPrice(productPrice);
    final BigDecimal sum = this.menuProducts.getTotalPrice();

    if (this.price.getPrice().compareTo(sum) > 0) {
      this.displayed = new MenuDisplayed(false);
    }
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name.getName();
  }

  public BigDecimal getPrice() {
    return price.getPrice();
  }

  public MenuGroup getMenuGroup() {
    return menuGroup;
  }

  public boolean isDisplayed() {
    return displayed.isDisplayed();
  }

  public MenuProducts getMenuProducts() {
    return menuProducts;
  }
}
