package kitchenpos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.Quantity;
import kitchenpos.products.tobe.domain.Product;

public final class TobeFixtures {
    public static final long MENU_PRODUCT_QUANTITY = 2;
    public static final long PRODUCT_PRICE = 10_000;
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
        return new Menu("치킨메뉴", BigDecimal.valueOf(price), menuGroup(), displayed,
            List.of(menuProduct(), menuProduct()));
    }

    public static MenuGroup menuGroup() {
        return new MenuGroup("두마리메뉴");
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(UUID.randomUUID().toString(), new Quantity(MENU_PRODUCT_QUANTITY));
    }

    public static Product product() {
        return new Product(generateName(), BigDecimal.valueOf(PRODUCT_PRICE));
    }

    private static String generateName() {
        return "치킨" + ThreadLocalRandom.current().nextInt(1000);
    }
}
