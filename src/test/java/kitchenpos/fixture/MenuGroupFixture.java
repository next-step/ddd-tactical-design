package kitchenpos.fixture;

import kitchenpos.menus.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupFixture {

    public static final String DEFAULT_MENU_GROUP_NAME = "세트 메뉴";
    public static final String DOUBLE_CHICKEN_MENU_GROUP_NAME = "두마리 메뉴";

    private MenuGroupFixture() {
    }

    public static MenuGroup menuGroup() {
        return menuGroup(createMenuGroupId(), DEFAULT_MENU_GROUP_NAME);
    }

    public static MenuGroup menuGroup(final UUID uuid) {
        return menuGroup(uuid, DEFAULT_MENU_GROUP_NAME);
    }

    public static MenuGroup menuGroup(final String name) {
        return menuGroup(createMenuGroupId(), name);
    }

    public static MenuGroup menuGroup(final UUID uuid, final String name) {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(uuid);
        menuGroup.setName(name);
        return menuGroup;
    }

    private static UUID createMenuGroupId() {
        return UUID.randomUUID();
    }
}
