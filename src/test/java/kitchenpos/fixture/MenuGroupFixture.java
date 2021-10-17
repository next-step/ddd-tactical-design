package kitchenpos.fixture;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.menugroup.domain.model.MenuGroupV2;

public class MenuGroupFixture {

    private static final String MENU_GROUP_NAME_1 = "메뉴그룹1";
    private static final String MENU_GROUP_NAME_2 = "메뉴그룹2";
    private static final Profanities profanities = new FakeProfanities();

    public static MenuGroupV2 MENU_GROUP1() {
        return new MenuGroupV2(new DisplayedName(MENU_GROUP_NAME_1, profanities));
    }

    public static MenuGroupV2 MENU_GROUP2() {
        return new MenuGroupV2(new DisplayedName(MENU_GROUP_NAME_2, profanities));
    }

}
