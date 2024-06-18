package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.menu.MenuClient;
import kitchenpos.eatinorders.domain.menu.OrderMenu;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FakeMenuClient implements MenuClient {
  private final Map<UUID, OrderMenu> orderMenus = new HashMap<>();
  @Override
  public Map<UUID, OrderMenu> findMenuInfoByMenuIds(List<UUID> menuIds) {
    return menuIds.stream()
            .filter(orderMenus::containsKey)
            .map(orderMenus::get)
            .collect(Collectors.toMap(
                    OrderMenu::id, m-> m
            ));
  }

  public void add(UUID menuid, BigDecimal price, boolean isDisplayed){
    orderMenus.put(menuid, new OrderMenu(menuid, price, isDisplayed));
  }

}
