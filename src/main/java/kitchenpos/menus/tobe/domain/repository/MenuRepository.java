package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.Menu;

import java.util.List;
import java.util.UUID;

public interface MenuRepository {

    Menu save(final Menu menu);

    List<Menu> findAllByIdIn(final List<UUID> ids);
}
