package kitchenpos.menus.application;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.products.domain.ProductPriceChangedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MenuEventListener implements ApplicationListener<ProductPriceChangedEvent> {
  private final MenuRepository menuRepository;

  public MenuEventListener(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public void onApplicationEvent(ProductPriceChangedEvent event) {
    final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
    final BigDecimal productPrice = event.getProductPrice();

    for (final Menu menu : menus) {
      menu.productPriceChanges(productPrice);
    }
  }
}
