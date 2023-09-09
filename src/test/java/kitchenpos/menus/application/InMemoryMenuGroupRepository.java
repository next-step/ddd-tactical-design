package kitchenpos.menus.application;


import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;

import java.util.*;

public class InMemoryMenuGroupRepository implements ToBeMenuGroupRepository {
    private final Map<UUID, ToBeMenuGroup> menuGroups = new HashMap<>();

    @Override
    public ToBeMenuGroup save(final ToBeMenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<ToBeMenuGroup> findById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<ToBeMenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
