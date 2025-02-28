package kitchenpos.menu.tobe.fixture;

import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;

public class MenuGroupFixture {
    public static MenuGroup menuGroup(String name) {
        return MenuGroup.of(name);
    }
}
