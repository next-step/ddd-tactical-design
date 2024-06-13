package kitchenpos.eatinorders.tobe.domain.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredMenu;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredMenuReader;

public class FakeRegisteredMenuReader implements RegisteredMenuReader {
  private final Map<UUID, RegisteredMenu> menuMap = new HashMap<>();

  public FakeRegisteredMenuReader(List<RegisteredMenu> registeredMenuList) {
    for (RegisteredMenu registeredMenu : registeredMenuList) {
      this.menuMap.put(registeredMenu.getMenuId(), registeredMenu);
    }
  }

  @Override
  public List<RegisteredMenu> findAllByIdIn(List<UUID> menuIds) {
    return menuIds.stream()
        .map(menuMap::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
