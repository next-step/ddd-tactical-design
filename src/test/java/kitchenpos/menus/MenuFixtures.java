package kitchenpos.menus;

import static kitchenpos.products.ProductFixtures.product;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import kitchenpos.menus.domain.*;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;

public class MenuFixtures {

    public static UUID INVALID_ID = new UUID(0, 0);

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(String name) {
        return menuGroup(UUID.randomUUID(), name);
    }

    public static MenuGroup menuGroup(UUID id, String name) {
        MenuGroupName menuGroupName = new MenuGroupName(name);
        return new MenuGroup(id, menuGroupName);
    }

    public static Menu menu() {
        return menu(19_000L);
    }

    public static Menu menu(long price) {
        return new Menu(
            UUID.randomUUID(),
            new MenuName("후라이드+후라이드", new FakeProfanityCheckClient()),
            new MenuPrice(BigDecimal.valueOf(price)),
            menuGroup()
        );
    }

    public static MenuProduct menuProduct(Menu menu) {
        return menuProduct(menu, product().getId(), 2L);
    }

    public static MenuProduct menuProduct(Menu menu, UUID productId, long quantity) {
        return new MenuProduct(
            new Random().nextLong(),
            menu,
            productId,
            new MenuProductQuantity(quantity)
        );
    }
}
