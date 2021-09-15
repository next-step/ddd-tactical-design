package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.MenuGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {

    final Map<UUID, MenuGroup> menuGroupMap = new HashMap<>();

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        menuGroupMap.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(final UUID id) {
        return Optional.ofNullable(menuGroupMap.get(id));
    }
}
