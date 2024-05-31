package kitchenpos.menus.tobe.domain.menu_group;

public class MenuGroupFixture {
  public static MenuGroupName normalMenuGroupName() {
    return createMenuGroupName("메뉴그룹명");
  }

  public static MenuGroupName createMenuGroupName(String name) {
    return new MenuGroupName(name);
  }

  public static MenuGroup create(MenuGroupName menuGroupName) {
    return new MenuGroup(menuGroupName);
  }
}
