package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.domain.MenuGroupFixture.menuGroup;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.tobe.domain.Menu.MenuProductRequest;


public class MenuFixture {

    public static Menu menu() {
        return new Menu("menuName", BigDecimal.ZERO, menuGroup(), List.of(), text -> false);
    }

    public static Menu menu(List<MenuProductRequest> menuProductRequests, BigDecimal price) {
        return new Menu("menuName", price, menuGroup(), menuProductRequests, text -> false);
    }
}
