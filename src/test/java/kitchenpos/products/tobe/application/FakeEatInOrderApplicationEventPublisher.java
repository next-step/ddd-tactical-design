package kitchenpos.products.tobe.application;

import kitchenpos.eatinorders.domain.MenuPriceFoundEvent;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import org.springframework.context.ApplicationEventPublisher;

public class FakeEatInOrderApplicationEventPublisher implements ApplicationEventPublisher {
  private final MenuRepository menuRepository;

  public FakeEatInOrderApplicationEventPublisher(MenuRepository menuRepository) {
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
