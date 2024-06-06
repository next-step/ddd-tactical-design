package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeMenuGroupRepository {
    TobeMenuGroup save(TobeMenuGroup menuGroup);

    Optional<TobeMenuGroup> findById(UUID id);

    List<TobeMenuGroup> findAll();
}

