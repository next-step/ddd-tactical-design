package kitchenpos.menus.tobe.domain.menugroup;

import java.util.List;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    MenuGroup findById(MenuGroupId id);

    List<MenuGroup> findAll();
}

