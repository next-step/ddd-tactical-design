package kitchenpos.menus.tobe.domain;

import java.util.UUID;

public class MenuGroupFixtures {
    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), new MenuGroupName(name));
    }
}
