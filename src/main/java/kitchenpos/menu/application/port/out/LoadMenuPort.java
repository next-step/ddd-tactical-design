package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadMenuPort {
    List<Menu> findAll();
    Optional<Menu> findById(UUID id);
    List<Menu> findByProductId(UUID productId);
}
