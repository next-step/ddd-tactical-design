package kitchenpos.menus.tobe.domain.menu.infra;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    MenuEntity save(MenuEntity menuEntity);
    Optional<MenuEntity> findById(final Long id);
    List<MenuEntity> findAll();
    boolean findByName(String name);

}
