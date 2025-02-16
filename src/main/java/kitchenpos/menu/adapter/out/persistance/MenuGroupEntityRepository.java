package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGroupEntityRepository {
    MenuGroupEntity save(MenuGroupEntity menuGroup);

    Optional<MenuGroupEntity> findById(UUID id);

    List<MenuGroupEntity> findAll();
}

