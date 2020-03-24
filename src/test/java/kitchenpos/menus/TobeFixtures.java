package kitchenpos.menus;

import kitchenpos.menus.tobe.domain.menu.domain.Menu;
import kitchenpos.menus.tobe.domain.menu.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.menu.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.Arrays;

public class TobeFixtures {
    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long FRIED_CHICKEN_ID = 1L;

    public static Menu twoFriedChickens(Long menuGroupId) {
        return new Menu("후라이드+후라이드", BigDecimal.valueOf(19_000L),
                menuGroupId, Arrays.asList(menuProduct()));
    }

    public static Menu twoFriedChickens(BigDecimal price) {
        return new Menu("후라이드+후라이드", price,
                TWO_FRIED_CHICKENS_ID, Arrays.asList(menuProduct()));
    }

    public static Menu twoFriedChickens() {
        return new Menu("후라이드+후라이드", new BigDecimal("19000.00"),
                TWO_FRIED_CHICKENS_ID, Arrays.asList(menuProduct()));
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(FRIED_CHICKEN_ID, 2);
    }

    public static MenuGroup twoChickens() {
        return new MenuGroup("두마리메뉴");
    }
}
