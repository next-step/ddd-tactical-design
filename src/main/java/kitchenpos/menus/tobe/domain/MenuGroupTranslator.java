package kitchenpos.menus.tobe.domain;

import kitchenpos.menugroups.domain.MenuGroup;

import java.util.UUID;

public interface MenuGroupTranslator {
    MenuGroup getMenuGroup(UUID menuGroupId);
}
