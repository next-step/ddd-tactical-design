package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Fixtures {
    private static final Long FIRED_MENU_GROUP_ID = 1L;
    private static final Long FIRED_MENU_PRODUCT_ID = 1L;

    public static Menu friedChicken() {
        String name = "한마리 치킨";
        BigDecimal price = BigDecimal.valueOf(16_000L);
        List<MenuProduct> menuProducts = menuProduct();

        return new Menu(name, price, FIRED_MENU_GROUP_ID, menuProducts);
    }

    public static List<MenuProduct> menuProduct() {
        long quantity = 3;
        return Arrays.asList(new MenuProduct(FIRED_MENU_PRODUCT_ID, quantity));
    }
}
