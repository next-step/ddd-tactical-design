package kitchenpos.menus.tobe.domain;

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
    sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

    menuProducts.stream().map(o -> o.get)

    if (price.compareTo(sum) > 0) {
      throw new IllegalArgumentException();
    }

    this.menuProducts = menuProducts;
  }

  public List<MenuProduct> getMenuProducts() {
    return menuProducts;
  }
}
