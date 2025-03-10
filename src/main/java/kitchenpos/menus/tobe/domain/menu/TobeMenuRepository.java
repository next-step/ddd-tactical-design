package kitchenpos.menus.tobe.domain.menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeMenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

    List<Menu> findAll();

    int countAllByIdIn(List<UUID> ids);
}

