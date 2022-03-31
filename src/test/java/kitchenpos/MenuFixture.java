package kitchenpos;

import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;

public class MenuFixture {

    public static MenuGroup 메뉴그룹() {
        return 메뉴그룹("메뉴그룹");
    }

    public static MenuGroup 메뉴그룹(final String name) {
        return new MenuGroup(name);
    }

}
