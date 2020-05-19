package kitchenpos.menus.bo;

import kitchenpos.AbstractInMemoryJpaRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;

public class InMemoryMenuRepository extends AbstractInMemoryJpaRepository<Menu, Long> implements MenuRepository {

}
