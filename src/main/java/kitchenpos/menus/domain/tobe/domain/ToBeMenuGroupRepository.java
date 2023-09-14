package kitchenpos.menus.domain.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToBeMenuGroupRepository {
    ToBeMenuGroup save(ToBeMenuGroup menuGroup);

    Optional<ToBeMenuGroup> findById(UUID id);

    List<ToBeMenuGroup> findAll();
}
