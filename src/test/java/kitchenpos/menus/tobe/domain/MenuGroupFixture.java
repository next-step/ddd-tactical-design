package kitchenpos.menus.tobe.domain;

public class MenuGroupFixture {

    public static MenuGroup menuGroup(String name) {
        return new MenuGroup(name);
    }

    public static MenuGroup menuGroup() {
        return MenuGroupFixture.menuGroup("치킨");
    }

}
