package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.entity.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;

public interface MenuRepository {
    Menu saveMenu(Menu menu);

    Optional<Menu> findMenuById(UUID id);

    List<Menu> findAllMenus();

    List<Menu> findMenusByIdIn(List<UUID> ids);

    List<Menu> findMenusByProductId(UUID productId);

    MenuGroup saveMenuGroup(MenuGroup menuGroup);

    Optional<MenuGroup> findMenuGroupById(UUID id);

    List<MenuGroup> findAllMenuGroups();
}

