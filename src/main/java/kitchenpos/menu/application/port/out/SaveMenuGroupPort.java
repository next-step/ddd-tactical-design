package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.MenuGroup;

public interface SaveMenuGroupPort {
    MenuGroup save(MenuGroup menuGroup);
}
