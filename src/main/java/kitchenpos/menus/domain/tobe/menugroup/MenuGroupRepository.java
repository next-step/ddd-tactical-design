package kitchenpos.menus.domain.tobe.menugroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroupRequest);

    Optional<MenuGroup> findById(UUID id);

    List<MenuGroup> findAll();
}

