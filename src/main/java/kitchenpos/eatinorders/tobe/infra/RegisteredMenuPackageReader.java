package kitchenpos.eatinorders.tobe.infra;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredMenu;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredMenuReader;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.supports.domain.tobe.Price;
import org.springframework.stereotype.Component;

@Component
public class RegisteredMenuPackageReader implements RegisteredMenuReader {
  private final MenuRepository menuRepository;

  public RegisteredMenuPackageReader(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public List<RegisteredMenu> findAllByIdIn(List<UUID> menuIds) {
    return menuRepository.findAllByIdIn(menuIds)
        .stream()
        .map(menu -> new RegisteredMenu(menu.getId(), menu.isDisplayed(), new Price(menu.getPrice())))
        .toList();
  }
}
