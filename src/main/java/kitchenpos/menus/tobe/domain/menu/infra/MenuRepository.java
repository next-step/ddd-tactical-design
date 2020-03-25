package kitchenpos.menus.tobe.domain.menu.infra;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    MenuEntity register(MenuEntity menuEntity);
    Optional<MenuEntity> findById(final Long id);
    List<MenuEntity> findAll();
    long countByIdIn(final List<Long> ids);

}
