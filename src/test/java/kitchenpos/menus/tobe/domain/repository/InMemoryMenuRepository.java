package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.Menu;

import java.util.*;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<UUID, Menu> menus = new HashMap<>();

    @Override
    public Menu save(Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(menus.get(id));
    }
}
