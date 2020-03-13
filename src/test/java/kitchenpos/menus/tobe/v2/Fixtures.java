package kitchenpos.menus.tobe.v2;


import kitchenpos.menus.tobe.v2.domain.Menu;
import kitchenpos.menus.tobe.v2.domain.MenuGroup;

import java.math.BigDecimal;

public class Fixtures {

    public static Menu friedChikenMenu() {
        String name = "후라이드";
        BigDecimal price = BigDecimal.TEN;
        return new Menu(name, price);
    }

    public static MenuGroup friedMenuGroup() {
        return new MenuGroup("후라이드");
    }

    public static MenuGroup garlicMenuGroup() {
        return new MenuGroup("마늘");
    }
}
