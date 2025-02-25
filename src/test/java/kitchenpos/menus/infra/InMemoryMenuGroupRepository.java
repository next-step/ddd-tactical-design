package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupId;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {
    private final Map<MenuGroupId, MenuGroup> menuGroups = new ConcurrentHashMap<>();

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(final MenuGroupId id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<MenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
