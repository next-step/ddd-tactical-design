package kitchenpos.menus.tode.domain;

import kitchenpos.common.tobe.DisplayedName;

import java.util.UUID;

public class Fixtures {

    public static MenuGroup MENU_GROUP_WITH_ALL(final UUID id, final String name) {
        return  new MenuGroup(id, new DisplayedName(name));
    }

    public static MenuGroup MENU_GROUP_WITH_ID(final UUID id) {
        return MENU_GROUP_WITH_ALL(id, "추천메뉴");
    }

    public static MenuGroup MENU_GROUP_WITH_NAME(final String name) {
        return MENU_GROUP_WITH_ALL(UUID.randomUUID(), name);
    }
}
