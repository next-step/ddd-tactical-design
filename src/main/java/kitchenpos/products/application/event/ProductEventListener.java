package kitchenpos.products.application.event;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.tobe.domain.event.ProductPriceChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {
  private final MenuService menuService;

  public ProductEventListener(MenuService menuService) {
    this.menuService = menuService;
  }

  @EventListener
  public void changePrice(final ProductPriceChangeEvent event) {
    menuService.changeProductPrice(event.getId());
  }
}
