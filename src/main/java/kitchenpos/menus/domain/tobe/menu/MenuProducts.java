package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Embeddable
public class MenuProducts {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "menu")
  private List<MenuProduct> menuProducts = new ArrayList<>();

  protected MenuProducts() {
  }

  public static MenuProducts of() {
    return new MenuProducts();
  }

  public BigDecimal sum() {
    return menuProducts
            .stream()
            .map(MenuProduct::amount)
            .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

  }
  @EventListener
  public void changeMenuProductPrice(final UUID productId, final Long price) {
    menuProducts.stream()
            .filter(menuProduct -> menuProduct.getId().equals(productId))
            .forEach(menuProduct -> menuProduct.changePrice(price));
  }

  public void add(MenuProduct menuProduct){
    this.menuProducts.add(menuProduct);
  }

  public boolean findByProductId(UUID productId){
    return this.menuProducts.stream().anyMatch(product -> product.getId().equals(productId));
  }
}
