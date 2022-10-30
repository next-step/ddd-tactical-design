package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.entity.MenuGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    List<MenuGroup> findAll();

    Optional<MenuGroup> findById(UUID id);
}
