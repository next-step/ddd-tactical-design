package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    <S extends Menu> S save(S entity);

    Optional<Menu> findById(UUID id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<UUID> ids);

    List<Menu> findAllByProductId(UUID productId);
}
