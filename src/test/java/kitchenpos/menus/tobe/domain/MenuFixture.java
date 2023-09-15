package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.domain.MenuGroupFixture.menuGroup;

import java.math.BigDecimal;
import java.util.List;


public class MenuFixture {

    public static Menu menu() {
        return new Menu("menuName", BigDecimal.ZERO, menuGroup(), List.of(), text -> false);
    }

    public static Menu menu(List<MenuProduct> menuProducts, BigDecimal price) {
        return new Menu("menuName", price, menuGroup(), menuProducts, text -> false);
    }
}
