package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.domain.menugroup.entity.MenuGroup;

public class Fixtures {

    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long FRIED_CHICKEN_ID = 1L;

    public static final Long TWO_CHICKENS_ID = 1L;
    public static final Long NONAME_MENUGROUP_ID = 2L;

    public static MenuGroup twoChickens() {
        return new MenuGroup.Builder()
                .id(TWO_CHICKENS_ID)
                .name("두마리메뉴")
                .build();
    }

    public static MenuGroup nonameMenuGroup(){
        return new MenuGroup.Builder()
                .id(NONAME_MENUGROUP_ID)
                .name(null)
                .build();
    }
}
