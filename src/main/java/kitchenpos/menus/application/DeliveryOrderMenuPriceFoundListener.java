package kitchenpos.menus.application;

import kitchenpos.deliveryorders.domain.MenuPriceFoundEvent;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderMenuPriceFoundListener
    implements ApplicationListener<MenuPriceFoundEvent> {
  private final MenuRepository menuRepository;

  public DeliveryOrderMenuPriceFoundListener(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public void onApplicationEvent(final MenuPriceFoundEvent event) {
    final Menu menu =
        menuRepository.findById(event.getMenuId()).orElseThrow(IllegalArgumentException::new);

    if (!menu.isDisplayed()) {
      throw new IllegalStateException();
    }

    if (menu.getPrice().compareTo(event.getPrice()) != 0) {
      throw new IllegalArgumentException();
    }
  }
}
