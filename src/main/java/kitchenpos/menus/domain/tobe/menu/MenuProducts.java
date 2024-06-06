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
  private List<MenuProduct> products = new ArrayList<>();

  protected MenuProducts() {
  }

  public static MenuProducts of() {
    return new MenuProducts();
  }

  public BigDecimal sum() {
    return products
            .stream()
            .map(MenuProduct::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

  }
  @EventListener
  public void changeMenuProductPrice(final UUID productId, final Long price) {
    products.stream()
            .filter(menuProduct -> menuProduct.getId().equals(productId))
            .forEach(menuProduct -> menuProduct.changePrice(price));
  }

  public void add(MenuProduct menuProduct){
    this.products.add(menuProduct);
  }

  public boolean findByProductId(UUID productId){
    return this.products.stream().anyMatch(product -> product.getId().equals(productId));
  }
}
