package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.*;

@Embeddable
public class MenuProducts {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "menu")
  private List<MenuProduct> products = new ArrayList<>();

  protected MenuProducts() {
  }

  private MenuProducts(List<MenuProduct> menuProducts) {
    products.addAll(menuProducts);
  }

  public static MenuProducts of(MenuProduct... menuProducts) {
    return new MenuProducts(List.of(menuProducts));
  }

  public static MenuProducts of(List<MenuProduct> menuProducts) {
    return new MenuProducts(menuProducts);
  }
  protected BigDecimal sum() {
    return products
            .stream()
            .map(MenuProduct::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

  }

  protected void changeMenuProductPrice(final UUID productId, final Long price) {
    products.stream()
            .filter(menuProduct -> menuProduct.getId().equals(productId))
            .forEach(menuProduct -> menuProduct.changePrice(price));
  }

  protected void addMenuProduct(MenuProduct menuProduct){
    this.products.add(menuProduct);
  }

  protected boolean containsZeroProducts(){
    return this.products.isEmpty();
  }

  public List<MenuProduct> getProducts() {
    return products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MenuProducts that = (MenuProducts) o;
    return Objects.equals(products, that.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products);
  }
}
