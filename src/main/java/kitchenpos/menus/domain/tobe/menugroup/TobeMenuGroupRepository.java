package kitchenpos.menus.domain.tobe.menugroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeMenuGroupRepository {
    MenuGroup save(MenuGroup menuGroup);

    Optional<MenuGroup> findById(UUID id);

    List<MenuGroup> findAll();
}

