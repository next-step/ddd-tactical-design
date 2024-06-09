package kitchenpos.menus.tobe.domain.repository;


import kitchenpos.menus.tobe.domain.entity.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;

public class InMemoryMenuRepository implements MenuRepository {
    private final Map<UUID, Menu> menus = new HashMap<>();
    private final Map<UUID, MenuGroup> menuGroups = new HashMap<>();

    @Override
    public Menu saveMenu(final Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findMenuById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAllMenus() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findMenusByIdIn(final List<UUID> ids) {
        return menus.values()
                    .stream()
                    .filter(menu -> ids.contains(menu.getId()))
                    .toList();
    }

    @Override
    public List<Menu> findMenusByProductId(final UUID productId) {
        return menus.values()
                    .stream()
                    .filter(menu -> menu.getMenuProducts().stream()
                                        .anyMatch(menuProduct -> menuProduct.getProductId().equals(productId)))
                    .toList();
    }

    @Override
    public MenuGroup saveMenuGroup(final MenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findMenuGroupById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }


    @Override
    public List<MenuGroup> findAllMenuGroups() {
        return new ArrayList<>(menuGroups.values());
    }
}
