package kitchenpos.menus.tobe.domain.repository;



import kitchenpos.menus.tobe.domain.entity.Menu;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

}
