package kitchenpos.menus.bo;

import kitchenpos.AbstractInMemoryJpaRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;

public class InMemoryMenuRepository extends AbstractInMemoryJpaRepository<Menu, Long> implements
    MenuRepository {

}
