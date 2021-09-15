package kitchenpos.menus.domain.tobe.domain.menugroup;

import java.util.List;
import java.util.Optional;
import kitchenpos.common.domain.MenuGroupId;

public interface MenuGroupRepository {

    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(MenuGroupId id);

    List<MenuGroup> findAll();
}
