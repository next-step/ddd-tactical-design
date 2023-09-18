package kitchenpos.menu.application.menugroup.port.in;

import kitchenpos.support.vo.Name;

public interface MenuGroupUseCase {

    MenuGroupDTO register(final Name name);
}
