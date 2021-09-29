package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<UUID, Menu> menuMap = new HashMap<>();

    @Override
    public Menu save(final Menu menu) {
        menuMap.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public List<Menu> findAllByIdIn(final List<UUID> ids) {
        return menuMap.values()
                .stream()
                .filter(menu -> ids.contains(menu.getId()))
                .collect(Collectors.toList());
    }
}
