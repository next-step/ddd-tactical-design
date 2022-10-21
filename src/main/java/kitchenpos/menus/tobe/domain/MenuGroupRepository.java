package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(Long id);

    List<MenuGroup> findAll();
}
