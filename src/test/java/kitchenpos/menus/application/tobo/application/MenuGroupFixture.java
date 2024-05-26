package kitchenpos.menus.application.tobo.application;

import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupFixture {

    public static MenuGroup createMenuGroupRequest() {
        return createMenuGroupRequest("두마리메뉴");
    }

    public static MenuGroup createMenuGroupRequest(final String name) {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(name);
        return menuGroup;
    }

}
