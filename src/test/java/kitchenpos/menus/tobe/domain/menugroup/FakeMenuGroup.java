package kitchenpos.menus.tobe.domain.menugroup;

public class FakeMenuGroup extends MenuGroup {

    public static MenuGroup createFake(String name) {
        return MenuGroup.create(MenuGroupName.create(name));
    }
}
