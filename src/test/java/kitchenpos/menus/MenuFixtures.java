package kitchenpos.menus;

import java.math.BigDecimal;
import java.util.List;
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
            menuGroup(),
            new MenuProducts(List.of(menuProduct()))
        );
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(
            UUID.randomUUID(),
            new MenuProductPrice(BigDecimal.valueOf(20_000L)),
            new MenuProductQuantity(1)
        );
    }
}
