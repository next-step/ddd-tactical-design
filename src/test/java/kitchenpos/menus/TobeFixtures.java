package kitchenpos.menus;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.Quantity;

public final class TobeFixtures {
    public static final long MENU_PRODUCT_QUANTITY = 2;
    public static final long MENU_PRODUCT_PRICE = 10_000;
    public static final long MENU_PRICE = 40_000;

    public static Menu menu() {
        return menu(MENU_PRICE, true);
    }

    public static Menu menu(boolean displayed) {
        return menu(MENU_PRICE, displayed);
    }

    public static Menu menu(long price) {
        return menu(price, true);
    }

    public static Menu menu(long price, boolean displayed) {
        return new Menu(new DisplayedName("치킨메뉴"), new Price(BigDecimal.valueOf(price)),
            menuGroup(), displayed,
            new MenuProducts(menuProduct(), menuProduct()));
    }

    public static MenuGroup menuGroup() {
        return new MenuGroup("두마리메뉴");
    }

    public static MenuProduct menuProduct() {
        return menuProduct(MENU_PRODUCT_PRICE, MENU_PRODUCT_QUANTITY);
    }

    public static MenuProduct menuProduct(long price, long quantity) {
        return new MenuProduct(UUID.randomUUID().toString(),
            new Price(new BigDecimal(price)), new Quantity(quantity));
    }
}
