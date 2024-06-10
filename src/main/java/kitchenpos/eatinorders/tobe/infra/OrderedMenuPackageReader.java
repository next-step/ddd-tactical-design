package kitchenpos.eatinorders.tobe.infra;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderedMenu;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderedMenuReader;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.supports.domain.tobe.Price;
import org.springframework.stereotype.Component;

@Component
public class OrderedMenuPackageReader implements OrderedMenuReader {
  private final MenuRepository menuRepository;

  public OrderedMenuPackageReader(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public List<OrderedMenu> findAllByIdIn(List<UUID> menuIds) {
    return menuRepository.findAllByIdIn(menuIds)
        .stream()
        .map(menu -> new OrderedMenu(menu.getId(), menu.isDisplayed(), new Price(menu.getPrice())))
        .toList();
  }
}
