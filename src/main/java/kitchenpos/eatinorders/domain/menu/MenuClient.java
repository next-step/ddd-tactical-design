package kitchenpos.eatinorders.domain.menu;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MenuClient {
  Map<UUID, OrderMenu> findMenuInfoByMenuIds(List<UUID> menuIds);
}
