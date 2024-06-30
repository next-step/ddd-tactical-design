package kitchenpos.products.tobe.application;

import java.util.List;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.products.domain.ProductPriceChangedEvent;
import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
  private final MenuRepository menuRepository;

  public FakeApplicationEventPublisher(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public void publishEvent(final Object event) {
    if (event instanceof ProductPriceChangedEvent) {
      final ProductPriceChangedEvent productPriceChangedEvent = (ProductPriceChangedEvent) event;

      final List<Menu> menus =
          menuRepository.findAllByProductId(productPriceChangedEvent.getProductId());
      for (Menu menu : menus) {
        menu.productPriceChanges(productPriceChangedEvent.getProductPrice());
      }
    }
  }
}
