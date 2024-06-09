package kitchenpos.eatinorders.tobe.domain.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderedMenu;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderedMenuReader;

public class FakeOrderedMenuReader implements OrderedMenuReader {
  private final Map<UUID, OrderedMenu> menuMap = new HashMap<>();

  public FakeOrderedMenuReader(List<OrderedMenu> orderedMenuList) {
    for (OrderedMenu orderedMenu : orderedMenuList) {
      this.menuMap.put(orderedMenu.getMenuId(), orderedMenu);
    }
  }

  @Override
  public List<OrderedMenu> findAllByIdIn(List<UUID> menuIds) {
    return menuIds.stream()
        .map(menuMap::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
