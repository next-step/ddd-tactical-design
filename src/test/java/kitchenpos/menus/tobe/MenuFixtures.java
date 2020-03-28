package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.menuGroup.domain.MenuGroup;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;

public class MenuFixtures {
    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long MENUGROUP_TWO_CHICKENS_ID = 1L;
    public static final Long PRODUCT_FRIED_CHICKEN_ID = 1L;

    public static Menu menuTwoFriedChickens() {
        final Menu menu = new Menu("후라이드+후라이드", BigDecimal.valueOf(19_000L), MENUGROUP_TWO_CHICKENS_ID, Arrays.asList(menuProduct()));
        ReflectionTestUtils.setField(menu, "id", TWO_FRIED_CHICKENS_ID);
        return menu;
    }

    public static MenuGroup menuGroupTwoChickens() {
        final MenuGroup menuGroup = new MenuGroup("두마리메뉴");
        ReflectionTestUtils.setField(menuGroup, "id", MENUGROUP_TWO_CHICKENS_ID);
        return menuGroup;
    }

    private static MenuProduct menuProduct() {
        final MenuProduct menuProduct = new MenuProduct(PRODUCT_FRIED_CHICKEN_ID, BigDecimal.valueOf(20000), 2L);
        return menuProduct;
    }
}
