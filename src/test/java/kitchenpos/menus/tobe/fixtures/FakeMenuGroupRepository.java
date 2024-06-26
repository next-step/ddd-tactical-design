package kitchenpos.menus.tobe.fixtures;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;

import java.util.*;

public class FakeMenuGroupRepository implements MenuGroupRepository {

    private Map<UUID, MenuGroup> inMemory = new HashMap<>();

    @Override
    public MenuGroup save(MenuGroup menuGroup) {
        inMemory.put(menuGroup.getId(), menuGroup);
        return inMemory.get(menuGroup.getId());
    }

    @Override
    public Optional<MenuGroup> findById(UUID id) {
        return Optional.ofNullable(inMemory.get(id));
    }

    @Override
    public List<MenuGroup> findAll() {
        return inMemory.values().stream().toList();
    }
}
