package kitchenpos.menus.tobe.infra;

import java.util.List;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.infra.ProductPriceChangesEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MenuEventListener implements ApplicationListener<ProductPriceChangesEvent> {
  private final MenuRepository menuRepository;

  public MenuEventListener(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public void onApplicationEvent(ProductPriceChangesEvent event) {
    final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());

    for (final Menu menu : menus) {
      menu.productPriceChanges();
    }
  }
}
