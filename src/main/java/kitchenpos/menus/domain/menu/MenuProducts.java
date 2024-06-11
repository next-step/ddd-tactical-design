package kitchenpos.menus.domain.menu;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Embeddable
public class MenuProducts {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "menu_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_product_to_menu"))
  private List<MenuProduct> menuProducts;

  protected MenuProducts() {}

  protected MenuProducts(List<MenuProduct> menuProducts, MenuPrice menuPrice) {
    BigDecimal sum = BigDecimal.ZERO;
    for (MenuProduct menuProduct : menuProducts) {
      sum = sum.add(menuProduct.multiplyByProductPriceAndQuantity());
    }

    if (menuPrice.getPrice().compareTo(sum) > 0) {
      throw new IllegalArgumentException();
    }

    this.menuProducts = menuProducts;
  }

  public BigDecimal getTotalPrice() {
    BigDecimal sum = BigDecimal.ZERO;
    for (MenuProduct menuProduct : this.menuProducts) {
      final BigDecimal quantity = BigDecimal.valueOf(menuProduct.getQuantity());
      sum = sum.add(menuProduct.getProductPrice().multiply(quantity));
    }

    return sum;
  }

  public void changeProductPrice(final BigDecimal changePrice) {
    for (MenuProduct menuProduct : menuProducts) {
      menuProduct.changeProductPrice(changePrice);
    }
  }

  public List<MenuProduct> getMenuProducts() {
    return menuProducts;
  }
}
