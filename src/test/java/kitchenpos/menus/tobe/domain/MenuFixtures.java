package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import kitchenpos.menus.tobe.domain.vo.MenuName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.products.application.FakePurgomalumClient;

public final class MenuFixtures {

    private MenuFixtures() {
    }

    public static MenuGroup menuGroup(String name) {
        return new MenuGroup(new MenuGroupName(name));
    }

    public static MenuName menuName(String name) {
        return new MenuName(name, new FakePurgomalumClient());
    }

    public static MenuPrice menuPrice(BigDecimal price, MenuProducts menuProducts) {
        return new MenuPrice(price, menuProducts);
    }

    public static MenuProduct menuProduct(long quantity, BigDecimal productPrice) {
        return new MenuProduct(
                UUID.randomUUID(),
                new MenuProductQuantity(quantity),
                productPrice
        );
    }

    public static MenuProducts menuProducts(MenuProduct... menuProducts) {
        return new MenuProducts(List.of(menuProducts));
    }
}
