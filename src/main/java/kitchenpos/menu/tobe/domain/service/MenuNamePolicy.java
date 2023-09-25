package kitchenpos.menu.tobe.domain.service;

import kitchenpos.menu.tobe.domain.MenuName;

public interface MenuNamePolicy {

    void validate(MenuName menuName);
}
