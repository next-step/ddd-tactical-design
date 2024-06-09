package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProducts {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "menu")
  private List<MenuProduct> products = new ArrayList<>();

  protected MenuProducts() {
  }
  @Deprecated
  private MenuProducts(List<MenuProduct> menuProducts) {
    products.addAll(menuProducts);
  }
  private MenuProducts(List<MenuProduct> menuProducts, ProductClient productClient) {
    validate(menuProducts, productClient);
    products.addAll(menuProducts);
  }
  private void validate(List<MenuProduct> menuProducts, ProductClient productClient) {
    if (Objects.isNull(menuProducts)) {
      throw new IllegalArgumentException("메뉴는 1개이상의 메뉴상품으로 구성되어야 합니다.");
    }

    if (productClient.countProductIds(getMenuProductIds(menuProducts)) != menuProducts.size()) {
      throw new IllegalArgumentException("메뉴상품들 중에 상품군에 존재하지 않는 상품이 있습니다.");
    }
  }

  public static MenuProducts of(ProductClient productClient, MenuProduct... menuProducts) {
    return new MenuProducts(List.of(menuProducts), productClient);
  }
  @Deprecated
  public static MenuProducts of(MenuProduct... menuProducts) {
    return new MenuProducts(List.of(menuProducts));
  }
  public static MenuProducts of(ProductClient productClient, List<MenuProduct> menuProducts) {
    return new MenuProducts(menuProducts, productClient);
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

  protected void addMenuProduct(MenuProduct menuProduct) {
    this.products.add(menuProduct);
  }

  protected boolean containsZeroProducts() {
    return this.products.isEmpty();
  }

  protected List<MenuProduct> getProducts() {
    return products;
  }

  protected List<UUID> getMenuProductIds(List<MenuProduct> menuProducts) {
    return menuProducts.stream()
            .map(MenuProduct::getId)
            .toList();
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
