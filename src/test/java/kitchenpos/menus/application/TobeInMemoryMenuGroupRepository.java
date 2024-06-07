package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TobeInMemoryMenuGroupRepository implements TobeMenuGroupRepository {
    private final Map<UUID, TobeMenuGroup> menuGroups = new HashMap<>();

    @Override
    public TobeMenuGroup save(final TobeMenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<TobeMenuGroup> findById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<TobeMenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
