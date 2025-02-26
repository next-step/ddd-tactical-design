package kitchenpos.menu.domain.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupId;

public interface MenuGroupRepository {

    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findByMenuGroupId(MenuGroupId id);

    List<MenuGroup> findAll();
}

