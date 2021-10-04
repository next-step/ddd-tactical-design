package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;

import java.util.*;

public class TobeInMemoryMenuGroupRepository implements TobeMenuGroupRepository {
    private Map<UUID, TobeMenuGroup> menuGroups = new HashMap<>();

    @Override
    public TobeMenuGroup save(TobeMenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<TobeMenuGroup> findById(UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<TobeMenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
