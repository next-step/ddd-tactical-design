package kitchenpos.menu;

import kitchenpos.menu.domain.menu.MenuName;
import kitchenpos.menu.domain.menu.MenuNameAccessor;
import kitchenpos.menu.domain.menu.MenuPrice;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;
import kitchenpos.support.vo.Name;

public final class Fixtures {

    public static String TEST_RAW_MENU_GROUP_NAME = "추천메뉴";
    public static MenuGroupNew TEST_MENU_GROUP
        = MenuGroupNew.create(Name.create(TEST_RAW_MENU_GROUP_NAME));


    public static String TEST_RAW_NAME = "후라이드 치킨 + 양념 치킨";
    public static MenuName TEST_MENU_NAME = MenuNameAccessor.create(Name.create(TEST_RAW_NAME));

    public static int TEST_RAW_MENU_PRICE = 3_000;
    public static MenuPrice TEST_MENU_PRICE = MenuPrice.create(TEST_RAW_MENU_PRICE);

    private Fixtures() {
        throw new UnsupportedOperationException();
    }
}
