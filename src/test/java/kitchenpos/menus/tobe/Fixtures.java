package kitchenpos.menus.tobe;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.commons.domain.Price;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;

public class Fixtures {

    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long TWO_CHICKENS_ID = 1L;
    public static final Long FRIED_CHICKEN_ID = 1L;

    public static Menu twoFriedChickens() {
        return new Menu(TWO_FRIED_CHICKENS_ID, "후라이드 + 후라이드",
            Price.valueOf(BigDecimal.valueOf(19_000L)), twoChickens(),
            Arrays.asList(menuProduct()));
    }

    public static MenuGroup twoChickens() {
        return new MenuGroup(TWO_CHICKENS_ID, "두 마리 메뉴");
    }

    public static MenuProduct menuProduct() {
        Menu menu = new Menu(TWO_FRIED_CHICKENS_ID, "후라이드 + 후라이드",
            Price.valueOf(BigDecimal.valueOf(19_000L)), twoChickens(), null);
        return new MenuProduct(null, menu, FRIED_CHICKEN_ID, 2L);
    }
}

