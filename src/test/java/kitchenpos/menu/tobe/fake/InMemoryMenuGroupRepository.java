package kitchenpos.menu.tobe.fake;



import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;

import java.util.*;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {
    private final Map<UUID, MenuGroup> store = new HashMap<>();

    @Override
    public MenuGroup save(MenuGroup menuGroup) {

        store.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<MenuGroup> findAll() {
        return new ArrayList<>(store.values());
    }
}
