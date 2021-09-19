package kitchenpos.menus.tobe.domain.menugroup.fixture;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.Name;

public class MenuGroupFixture {
	public static MenuGroup 메뉴그룹(String name) {
		return new MenuGroup(new Name(name));
	}
}
