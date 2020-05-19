package kitchenpos.menus.bo;

import kitchenpos.AbstractInMemoryJpaRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;

public class InMemoryMenuGroupRepository extends AbstractInMemoryJpaRepository<MenuGroup, Long> implements MenuGroupRepository {

}
