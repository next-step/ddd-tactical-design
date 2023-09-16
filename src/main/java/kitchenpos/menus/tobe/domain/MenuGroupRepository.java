package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGroupRepository {
    NewMenuGroup save(NewMenuGroup newMenuGroup);

    Optional<NewMenuGroup> findById(UUID id);

    List<NewMenuGroup> findAll();
}

