package kitchenpos.fixture;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;

public class MenuGroupFixture {

    private static final String MENU_GROUP_NAME_1 = "메뉴그룹1";
    private static final String MENU_GROUP_NAME_2 = "메뉴그룹2";
    private static final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    public static MenuGroup MENU_GROUP1() {
        return new MenuGroup(new DisplayedName(MENU_GROUP_NAME_1, purgomalumClient));
    }

    public static MenuGroup MENU_GROUP2() {
        return new MenuGroup(new DisplayedName(MENU_GROUP_NAME_2, purgomalumClient));
    }

}
