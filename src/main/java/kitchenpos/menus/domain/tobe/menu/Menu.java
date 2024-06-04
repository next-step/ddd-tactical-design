package kitchenpos.menus.domain.tobe.menu;


import jakarta.persistence.*;
import kitchenpos.products.domain.tobe.ProfanityValidator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;


@Table(name = "menu")
@Entity
public class Menu {

  public static final int ZERO = 0;
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;
  @Embedded
  @Column(name = "name", nullable = false)
  private MenuName menuName;
  @Embedded
  @Column(name = "price", nullable = false)
  private MenuPrice menuPrice;

  @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
  private UUID menuGroupId;
  @Column(name = "displayed", nullable = false)
  private boolean displayed;

  @Embedded
  private MenuProducts menuProducts;

  protected Menu() {
  }

  private Menu(MenuName menuName, MenuPrice menuPrice, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
    validate(menuPrice, menuProducts.sum(), menuProducts);

    this.id = UUID.randomUUID();
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.menuGroupId = menuGroupId;
    this.displayed = displayed;
    this.menuProducts = menuProducts;
  }

  public static Menu of(final String name, final Long price, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts, final ProfanityValidator profanityValidator) {

    return new Menu(MenuName.of(name, profanityValidator), MenuPrice.of(price), menuGroupId, displayed, menuProducts);
  }

  private void validate(final MenuPrice menuPrice, final BigDecimal price, final MenuProducts menuProducts) {
    if (menuPrice.isBigger(price)) {
      throw new IllegalArgumentException("`메뉴 상품 가격`의 총액보다 `메뉴 가격`이 클 수 없다.");
    }

    if (menuProducts.sum().compareTo(BigDecimal.ZERO) > ZERO) {
      throw new IllegalArgumentException("`메뉴`의 `메뉴 가격`은 양수이어야한다.");
    }
  }

  public void hide() {
    this.displayed = false;
  }

  public void display() {
    validate(menuPrice, menuProducts.sum(), menuProducts);
    this.displayed = true;
  }

  public void changePrice(BigDecimal price) {
    MenuPrice menuPriceRequest = MenuPrice.of(price);
    validate(menuPriceRequest, menuProducts.sum(), menuProducts);

    this.menuPrice = menuPriceRequest;
  }

  public UUID getId() {
    return id;
  }

  public MenuProducts getMenuProducts() {
    return getMenuProducts();
  }

  public boolean isDisplayed() {
    return displayed;
  }

  public MenuPrice getMenuPrice() {
    return menuPrice;
  }
}
