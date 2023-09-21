package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.UUID;


public interface MenuRepository {
    Menu save(Menu menu);

    List<Menu> findAllByIdIn(List<UUID> ids);
}

