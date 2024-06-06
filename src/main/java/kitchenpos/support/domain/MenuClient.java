package kitchenpos.support.domain;

import kitchenpos.menus.tobe.domain.menu.Menu;

import java.util.List;
import java.util.UUID;

public interface MenuClient {
    Menu getMenu(UUID menuId);

    int countMenusByIdIn(List<UUID> menuIds);
}
