package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.MenuGroup;

import java.util.Optional;
import java.util.UUID;

public interface MenuGroupRepository {

    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(UUID id);
}
