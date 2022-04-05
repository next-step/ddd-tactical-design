package kitchenpos.menus.application;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;

import java.util.*;

public class InMemoryTobeMenuGroupRepository implements TobeMenuGroupRepository {
    private final Map<MenuGroupId, TobeMenuGroup> menuGroups = new HashMap<>();

    @Override
    public TobeMenuGroup save(final TobeMenuGroup menuGroup) {
        menuGroups.put(menuGroup.getId(), menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<TobeMenuGroup> findById(final MenuGroupId id) {
        return Optional.ofNullable(menuGroups.get(id));
    }

    @Override
    public List<TobeMenuGroup> findAll() {
        return new ArrayList<>(menuGroups.values());
    }
}
