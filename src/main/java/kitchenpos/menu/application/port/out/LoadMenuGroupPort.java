package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.MenuGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadMenuGroupPort {
    List<MenuGroup> findAll();
    Optional<MenuGroup> findById(UUID id);
}
