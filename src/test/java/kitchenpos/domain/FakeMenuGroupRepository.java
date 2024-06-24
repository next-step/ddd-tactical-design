package kitchenpos.domain;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;

import java.util.*;

public class FakeMenuGroupRepository implements MenuGroupRepository {

    private final HashMap<UUID, MenuGroup> inMemory = new HashMap<>();

    @Override
    public MenuGroup save(MenuGroup menuGroup) {
        inMemory.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(UUID id) {
        return Optional.ofNullable(inMemory.get(id));
    }

    @Override
    public List<MenuGroup> findAll() {
        return new ArrayList<>(inMemory.values());
    }
}
