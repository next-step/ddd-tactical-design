package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

  private static final String MENU_PRODUCTS_MUST_NOT_BE_NULL_OR_EMPTY = "메뉴 상품은 반드시 1개 이상이어야 합니다.";

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
  private List<MenuProduct> menuProducts;

  protected MenuProducts() {
  }

  public MenuProducts(List<MenuProduct> menuProducts, Menu menu) {
    validateMenuProducts(menuProducts);
    connectRelations(menuProducts, menu);
    this.menuProducts = menuProducts;
  }

  private void validateMenuProducts(List<MenuProduct> menuProducts) {
    if (isMenuProductsNullOrEmpty(menuProducts)) {
      throw new IllegalArgumentException(MENU_PRODUCTS_MUST_NOT_BE_NULL_OR_EMPTY);
    }
  }

  private boolean isMenuProductsNullOrEmpty(List<MenuProduct> menuProducts) {
    return menuProducts == null || menuProducts.isEmpty();
  }

  private void connectRelations(List<MenuProduct> menuProducts, Menu menu) {
    menuProducts.forEach(menuProduct -> menuProduct.connectRelation(menu));
  }

  public MenuPrice sum() {
    return new MenuPrice(calculateSum());
  }

  private BigDecimal calculateSum() {
    return menuProducts.stream()
        .map(MenuProduct::calculateAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
