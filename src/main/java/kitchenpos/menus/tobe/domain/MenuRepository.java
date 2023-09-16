package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface MenuRepository {
    NewMenu save(NewMenu newMenu);

    Optional<NewMenu> findById(UUID id);

    List<NewMenu> findAll();

    List<NewMenu> findAllByIdIn(List<UUID> ids);

    List<NewMenu> findAllByProductId(UUID productId);
}

