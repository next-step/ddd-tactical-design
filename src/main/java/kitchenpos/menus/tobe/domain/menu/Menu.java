package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

@Entity
@Table(name = "menu")
public class Menu {

  private static final String MENU_GROUP_MUST_NOT_BE_NULL = "메뉴 그룹을 반드시 지정해야 합니다.";
  private static final String PRICE_MUST_NOT_OVER_MENU_PRODUCTS_AMOUNT_SUM = "메뉴 가격은 메뉴상품 금액의 합보다 작거나 같아야합니다. 메뉴 가격 : %d, 메뉴 상품 금액의 합 : %d";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private MenuName menuName;

  @Embedded
  private MenuPrice price;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "menu_group_id")
  private MenuGroup menuGroup;

  @Column(nullable = false)
  private boolean displayed;

  @Embedded
  private MenuProducts menuProducts;

  protected Menu() {
  }

  public Menu(String menuName, boolean menuNameProfanity, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
    this.menuName = new MenuName(menuName, menuNameProfanity);
    validateMenuGroup(menuGroup);
    this.menuGroup = menuGroup;
    this.displayed = displayed;
    this.menuProducts = new MenuProducts(menuProducts, this);

    MenuPrice menuPrice = new MenuPrice(price);
    validateMenuPrice(menuPrice);
    this.price = menuPrice;
  }

  private void validateMenuGroup(MenuGroup menuGroup) {
    if (isMenuGroupNull(menuGroup))  {
      throw new IllegalArgumentException(MENU_GROUP_MUST_NOT_BE_NULL);
    }
  }

  private boolean isMenuGroupNull(MenuGroup menuGroup) {
    return menuGroup == null;
  }

  private void validateMenuPrice(MenuPrice price) {
    MenuPrice menuProductsAmountSum = menuProducts.sum();
    if (isPriceOverMenuProductsAmountSum(price, menuProductsAmountSum)) {
      throw new IllegalArgumentException(String.format(PRICE_MUST_NOT_OVER_MENU_PRODUCTS_AMOUNT_SUM, price.longValue(), menuProductsAmountSum.longValue()));
    }
  }

  private boolean isPriceOverMenuProductsAmountSum(MenuPrice price, MenuPrice menuProductsAmountSum) {
    return price.compareTo(menuProductsAmountSum) > 0;
  }

  public void changePrice(MenuPrice newPrice) {
    validateMenuPrice(newPrice);
    price = newPrice;
  }

  public void show() {
    validateMenuPrice(price);
    displayed = true;
  }

  public void hide() {
    displayed = false;
  }

  public BigDecimal getPriceValue() {
    return price.value();
  }

  public boolean isDisplayed() {
    return displayed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Menu menu = (Menu) o;
    return Objects.equals(id, menu.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
