package kitchenpos.menus;

import java.util.UUID;
import kitchenpos.menus.domain.MenuGroup;

public class MenuGroupFixture {

    public static MenuGroup menuGroup(String name) {
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(name);

        return menuGroup;
    }

    public static MenuGroup menuGroup() {
        return MenuGroupFixture.menuGroup("치킨");
    }

}
