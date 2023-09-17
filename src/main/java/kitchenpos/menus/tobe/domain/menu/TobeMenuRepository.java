package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menu.TobeMenu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeMenuRepository {
    TobeMenu save(TobeMenu menu);

    Optional<TobeMenu> findById(UUID id);

    List<TobeMenu> findAll();

    List<TobeMenu> findAllByIdIn(List<UUID> ids);

    List<TobeMenu> findAllByProductId(UUID productId);
}
