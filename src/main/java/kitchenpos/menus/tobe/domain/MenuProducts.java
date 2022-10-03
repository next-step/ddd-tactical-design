package kitchenpos.menus.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "menu_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
  )
  private final List<MenuProduct> menuProducts;

  public MenuProducts() {
    this(new ArrayList<>());
  }

  public MenuProducts(List<MenuProduct> menuProducts) {
    this.menuProducts = menuProducts;
  }

  public Price totalSum() {
    return menuProducts.stream()
        .map(MenuProduct::amount)
        .reduce(Price.ZERO, Price::plus);
  }

  public List<MenuProduct> getMenuProducts() {
    return Collections.unmodifiableList(menuProducts);
  }
}
