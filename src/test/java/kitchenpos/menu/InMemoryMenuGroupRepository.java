package kitchenpos.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuGroupRepository;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {
    private final Map<UUID, MenuGroup> menuGroups = new HashMap<>();

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<MenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
