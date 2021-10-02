package kitchenpos.menus.tobe.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menugroup.model.MenuGroupV2;
import kitchenpos.menugroup.repository.MenuGroupRepositoryV2;

public class TobeInMemoryMenuGroupRepository implements MenuGroupRepositoryV2 {
    private final Map<UUID, MenuGroupV2> menuGroups = new HashMap<>();

    @Override
    public MenuGroupV2 save(final MenuGroupV2 menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroupV2> findById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<MenuGroupV2> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
