package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> findById(Long id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<Long> ids);

    List<Menu> findAllByProductId(Long productId);
}
