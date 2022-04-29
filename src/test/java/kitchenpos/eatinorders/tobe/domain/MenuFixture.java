package kitchenpos.eatinorders.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuFixture {

    public static Menu displayedMenu() {
        return menu(true);
    }

    public static Menu displayedMenu(int price) {
        return menu(true, BigDecimal.valueOf(price));
    }

    public static Menu hiddenMenu() {
        return menu(false);
    }

    private static Menu menu(boolean displayed) {
        return menu(displayed, BigDecimal.valueOf(10_000));
    }

    private static Menu menu(boolean displayed, BigDecimal price) {
        return new Menu(UUID.randomUUID(), price, displayed);
    }
}
