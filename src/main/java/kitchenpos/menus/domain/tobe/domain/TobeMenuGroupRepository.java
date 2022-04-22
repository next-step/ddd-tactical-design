package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;

import java.util.List;
import java.util.Optional;

public interface TobeMenuGroupRepository {
    TobeMenuGroup save(TobeMenuGroup menuGroup);

    Optional<TobeMenuGroup> findById(MenuGroupId id);

    List<TobeMenuGroup> findAll();
}

