package kitchenpos.legacy.menu.domain.repository;

import kitchenpos.legacy.menu.domain.model.MenuGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(UUID id);

    List<MenuGroup> findAll();
}

