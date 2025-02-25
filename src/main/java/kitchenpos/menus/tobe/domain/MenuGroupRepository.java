package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.MenuGroupId;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(MenuGroupId id);

    List<MenuGroup> findAll();
}
