package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;

public class Fixtures {
    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long TWO_CHICKENS_ID = 1L;
    public static final Long FRIED_CHICKEN_ID = 1L;

    public static Menu twoFriedChickens() {
        return MenuBuilder.aMenu()
                .withId(TWO_FRIED_CHICKENS_ID)
                .withName("후라이드+후라이드")
                .withMenuPrice(new MenuPrice(BigDecimal.valueOf(19_000L)))
                .withMenuGroupId(TWO_CHICKENS_ID)
                .withMenuProducts(Arrays.asList(menuProduct()))
                .build();
    }

    public static MenuGroup twoChickens() {
        return MenuGroupBuilder.aMenuGroup()
                .withId(TWO_CHICKENS_ID)
                .withName("두마리메뉴")
                .build();
    }

    private static MenuProduct menuProduct() {
        return MenuProductBuilder.aMenuProduct()
                .withMenuId(TWO_FRIED_CHICKENS_ID)
                .withProductId(FRIED_CHICKEN_ID)
                .withQuantity(2)
                .build();
    }
}
