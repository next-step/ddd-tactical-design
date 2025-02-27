package kitchenpos.fixture;

import kitchenpos.menu.domain.model.MenuGroup;

import java.util.UUID;

public class MenuGroupFixture {

    public static MenuGroup createMenuGroup(final String name) {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(name);
        return menuGroup;
    }

}
