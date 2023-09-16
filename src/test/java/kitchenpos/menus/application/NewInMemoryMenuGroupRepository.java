package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.NewMenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;

import java.util.*;

public class NewInMemoryMenuGroupRepository implements MenuGroupRepository {
    private final Map<UUID, NewMenuGroup> menuGroups = new HashMap<>();

    @Override
    public NewMenuGroup save(final NewMenuGroup newMenuGroup) {
        menuGroups.put(newMenuGroup.getId(), newMenuGroup);
        return newMenuGroup;
    }

    @Override
    public Optional<NewMenuGroup> findById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<NewMenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
