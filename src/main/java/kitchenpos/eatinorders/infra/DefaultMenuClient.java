package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.menu.MenuClient;
import kitchenpos.eatinorders.domain.menu.OrderMenu;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DefaultMenuClient implements MenuClient {
  private final MenuRepository menuRepository;

  public DefaultMenuClient(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }


  @Override
  public Map<UUID, OrderMenu> findMenuInfoByMenuIds(List<UUID> menuIds) {

    return menuRepository.findAllByIdIn(menuIds).stream()
            .map(m -> new OrderMenu(m.getId(), m.getMenuPrice(), m.isDisplayed()))
            .collect(Collectors.toMap(
                    OrderMenu::id, m-> m
            ));
  }
}
