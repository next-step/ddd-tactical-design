package kitchenpos.fixture;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuFixture {
    public static MenuGroup 메뉴그룹() {
        return 메뉴그룹("메뉴그룹");
    }

    public static MenuGroup 메뉴그룹(final String name) {
        return new MenuGroup(name);
    }

    public static MenuGroup 메뉴그룹(final UUID id, final String name) {
        return new MenuGroup(id, name);
    }
}
