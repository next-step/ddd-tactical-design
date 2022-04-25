package kitchenpos.menus.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MenuFixture {
    public static List<MenuProduct> MENU_PRODUCTS;

    static {
        MENU_PRODUCTS = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10_000), 1L),
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(5_000), 2L)
        );
    }

    public static Menu menu(String name, BigDecimal price, String menuGroupName) {
        return new Menu(name, price, menuGroupName, MENU_PRODUCTS);
    }
}
