package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuEntityRepository {
    MenuEntity save(MenuEntity menu);

    Optional<MenuEntity> findById(UUID id);

    List<MenuEntity> findAll();

    List<MenuEntity> findAllByIdIn(List<UUID> ids);

    List<MenuEntity> findAllByProductId(UUID productId);
}

