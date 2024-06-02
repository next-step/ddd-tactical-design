package kitchenpos.menus.domain.tobe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.domain.MenuGroup;

public interface MenuGroupRepository {
    kitchenpos.menus.domain.MenuGroup save(kitchenpos.menus.domain.MenuGroup menuGroup);

    Optional<kitchenpos.menus.domain.MenuGroup> findById(UUID id);

    List<MenuGroup> findAll();
}

