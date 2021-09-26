package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.Menu;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {

    Optional<Menu> findById(UUID id);

}
