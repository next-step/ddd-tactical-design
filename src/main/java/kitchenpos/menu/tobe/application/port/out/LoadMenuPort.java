package kitchenpos.menu.tobe.application.port.out;

import kitchenpos.menu.domain.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadMenuPort {
    // Not yet implemented

    List<Menu> findAllByIdIn(final List<UUID> ids);

    Optional<Menu> findById(final UUID id);
}
