package kitchenpos.eatinorders.tobe.domain.menu;

import java.util.List;
import java.util.UUID;

public interface MenuManager {
    Menu getMenu(final UUID menuId);

    List<Menu> getMenus(final List<UUID> menuIds);
}
