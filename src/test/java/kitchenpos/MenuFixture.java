package kitchenpos;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class MenuFixture {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return new Menu(UUID.randomUUID(), "후라이드+후라이드", new MenuPrice(BigDecimal.valueOf(price)), menuGroup(), displayed,
                new MenuProducts(Arrays.asList(menuProducts)));
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), name);
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(new Random().nextLong(), 2L, new Product(UUID.randomUUID(), BigDecimal.valueOf(16_000)));
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(new Random().nextLong(), quantity, new Product(product.getProductId(), product.getProductPrice()));
    }
}
