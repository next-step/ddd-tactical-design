package kitchenpos.menus.application.tobe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.menus.domain.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.domain.menugroup.MenuGroupRepository;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {

    private final Map<MenuGroupId, MenuGroup> menuGroups = new HashMap<>();

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
