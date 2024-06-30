package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.takeoutorders.domain.MenuPriceFoundEvent;
import org.springframework.context.ApplicationEventPublisher;

public class FakeTakeoutOrderApplicationEventPublisher implements ApplicationEventPublisher {
  private final MenuRepository menuRepository;

  public FakeTakeoutOrderApplicationEventPublisher(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public void publishEvent(final Object event) {
    if (event instanceof MenuPriceFoundEvent) {
      final MenuPriceFoundEvent menuPriceFoundEvent = (MenuPriceFoundEvent) event;

      final Menu menu =
          menuRepository
              .findById(menuPriceFoundEvent.getMenuId())
              .orElseThrow(IllegalArgumentException::new);
      if (!menu.isDisplayed()) {

        throw new IllegalStateException();
      }

      if (menu.getPrice().compareTo(menuPriceFoundEvent.getPrice()) != 0) {
        throw new IllegalArgumentException();
      }
    }
  }
}
