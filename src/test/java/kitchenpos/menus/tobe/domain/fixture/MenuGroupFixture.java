package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.menus.tobe.domain.model.MenuGroup;

import java.util.UUID;

public class MenuGroupFixture {

    public static MenuGroup MENU_GROUP_WITH_ALL_FIELDS(final UUID id, final String name) {
        return new MenuGroup(id, new DisplayedName(name));
    }

    public static MenuGroup MENU_GROUP_WITH_NAME(final String name) {
        return MENU_GROUP_WITH_ALL_FIELDS(UUID.randomUUID(), name);
    }

    public static MenuGroup DEFAULT_MENU_GROUP() {
        return MENU_GROUP_WITH_ALL_FIELDS(UUID.randomUUID(), "추천메뉴");
    }
}
