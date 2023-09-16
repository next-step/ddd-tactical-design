package kitchenpos.menugroups.domain;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(MenuGroupId id);

    List<MenuGroup> findAll();
}

