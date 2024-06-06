package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.domain.MenuGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGroupRepository {
    kitchenpos.menus.domain.MenuGroup save(kitchenpos.menus.domain.MenuGroup menuGroup);

    Optional<kitchenpos.menus.domain.MenuGroup> findById(UUID id);

    List<MenuGroup> findAll();
}

