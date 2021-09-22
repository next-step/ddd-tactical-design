package kitchenpos.fixture;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.model.MenuGroup;

public class MenuGroupFixture {

    private static final String MENU_GROUP_NAME_1 = "메뉴그룹1";
    private static final String MENU_GROUP_NAME_2 = "메뉴그룹2";
    private static final Profanities profanities = new FakeProfanities();

    public static MenuGroup MENU_GROUP1() {
        return new MenuGroup(new DisplayedName(MENU_GROUP_NAME_1, profanities));
    }

    public static MenuGroup MENU_GROUP2() {
        return new MenuGroup(new DisplayedName(MENU_GROUP_NAME_2, profanities));
    }

}
