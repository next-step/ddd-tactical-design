package kitchenpos.menu.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;

public interface MenuGroupRepository {

    MenuGroup save(final MenuGroup menuGroup);

    Optional<MenuGroup> findById(final UUID id);

    List<MenuGroup> findAll();
}
