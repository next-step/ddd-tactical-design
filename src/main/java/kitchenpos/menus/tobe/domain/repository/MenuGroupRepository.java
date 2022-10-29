package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.entity.MenuGroup;

import java.util.List;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    List<MenuGroup> findAll();
}
