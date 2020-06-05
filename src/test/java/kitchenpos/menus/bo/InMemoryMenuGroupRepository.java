package kitchenpos.menus.bo;

import kitchenpos.AbstractInMemoryJpaRepository;
import kitchenpos.menus.tobe.domain.group.MenuGroup;
import kitchenpos.menus.tobe.domain.group.MenuGroupRepository;

public class InMemoryMenuGroupRepository extends
    AbstractInMemoryJpaRepository<MenuGroup, Long> implements MenuGroupRepository {

}
