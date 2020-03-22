package kitchenpos.menus.tobe.menugroup.infra;

import kitchenpos.menus.tobe.menugroup.entity.MenuGroup;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository {
    MenuGroup save (MenuGroup menuGroup);
    Optional<MenuGroup> findById(Long id);
    List<MenuGroup> list();
<<<<<<< HEAD
    boolean findByName(String name);
=======
    boolean findByNameContaining(String name);
>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
}
