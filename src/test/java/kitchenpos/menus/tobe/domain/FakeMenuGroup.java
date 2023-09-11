package kitchenpos.menus.tobe.domain;

public class FakeMenuGroup extends MenuGroup {

    public static MenuGroup createFake(String name) {
        return MenuGroup.create(MenuGroupName.create(name));
    }
}
