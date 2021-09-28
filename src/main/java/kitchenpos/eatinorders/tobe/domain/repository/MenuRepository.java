package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.Menu;

public interface MenuRepository {

    Optional<Menu> findById(UUID id);

    List<Menu> findAllByIdIn(List<UUID> ids);
}

