package kitchenpos.menus.domain.tobe.menu;


import jakarta.persistence.*;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityValidator;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;


@Table(name = "menu")
@Entity
public class Menu {

  private static final int ZERO = 0;
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;
  @Embedded
  @Column(name = "name", nullable = false)
  private MenuName menuName;
  @Embedded
  @Column(name = "price", nullable = false)
  private Price menuPrice;

  @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
  private UUID menuGroupId;
  @Column(name = "displayed", nullable = false)
  private boolean displayed;

  @Embedded
  private MenuProducts menuProducts;

  protected Menu() {
  }

  private Menu(MenuName menuName, Price menuPrice, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
    validate(menuPrice, menuProducts.sum(), menuProducts, menuGroupId);

    this.id = UUID.randomUUID();
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.menuGroupId = menuGroupId;
    this.displayed = displayed;
    this.menuProducts = menuProducts;
  }

  public static Menu of(final String name, final Long price, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts, final ProfanityValidator profanityValidator) {

    return new Menu(MenuName.of(name, profanityValidator), Price.from(price), menuGroupId, displayed, menuProducts);
  }
  public static Menu of(final String name, final BigDecimal price, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts, final ProfanityValidator profanityValidator) {

    return new Menu(MenuName.of(name, profanityValidator), Price.from(price), menuGroupId, displayed, menuProducts);
  }
  private void validate(final Price menuPrice, final BigDecimal price, final MenuProducts menuProducts, final UUID menuGroupId) {
    if (Objects.isNull(menuGroupId)){
      throw new IllegalArgumentException("`메뉴는 특정 메뉴 그룹에 속해야 한다.");
    }

    if (menuProducts.containsZeroProducts()){
      throw new IllegalArgumentException("`메뉴상품`들이 한개 이상 있어야한다.");
    }

    if (menuPrice.isBigger(price)) {
      throw new IllegalArgumentException("`메뉴 상품 가격`의 총액보다 `메뉴 가격`이 클 수 없다.");
    }

  }

  public void hide() {
    this.displayed = false;
  }

  public void display() {
    validate(menuPrice, menuProducts.sum(), menuProducts, menuGroupId);
    this.displayed = true;
  }

  public void changePrice(BigDecimal price) {
    Price menuPriceRequest = Price.from(price);
    validate(menuPriceRequest, menuProducts.sum(), menuProducts, menuGroupId);

    this.menuPrice = menuPriceRequest;
  }

  public UUID getId() {
    return id;
  }

  public MenuProducts getMenuProducts() {
    return menuProducts;
  }

  public boolean isDisplayed() {
    return displayed;
  }

  public BigDecimal getMenuPrice() {
    return menuPrice.getPrice();
  }

  public String getMenuName() {
    return menuName.getName();
  }

  public UUID getMenuGroupId() {
    return menuGroupId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Menu menu = (Menu) o;
    return Objects.equals(id, menu.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
