package kitchenpos.menus.application.fixtures;

import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupFixture {
    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(name);
    }
}
