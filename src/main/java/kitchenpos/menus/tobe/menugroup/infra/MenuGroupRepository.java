package kitchenpos.menus.tobe.menugroup.infra;

import kitchenpos.menus.tobe.menugroup.entity.MenuGroup;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository {
    MenuGroup save (MenuGroup menuGroup);
    Optional<MenuGroup> findById(Long id);
    List<MenuGroup> list();
    boolean findByNameContaining(String name);
}
