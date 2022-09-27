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

    public static Menu menu(BigDecimal price, MenuProduct... menuProduct) {
        return menu(
                "후라이드+후라이드",
                "두마리 치킨",
                price,
                false,
                menuProduct
        );
    }

    public static Menu menu(BigDecimal menuPrice, BigDecimal menuProductAmount) {
        MenuProduct menuProduct = menuProduct(1, menuProductAmount);
        return new Menu(
                UUID.randomUUID(),
                menuName(" 후라이드+후라이드"),
                menuPrice(menuPrice),
                menuGroup("두마리 치킨"),
                false,
                menuProducts(menuProduct),
                UUID.randomUUID()
        );
    }

    public static Menu menu(
            String name,
            String groupName,
            BigDecimal price,
            boolean displayed,
            MenuProduct... menuProduct
    ) {
        MenuProducts menuProducts = menuProducts(menuProduct);
        return new Menu(
                menuName(name),
                menuGroup(groupName),
                menuProducts,
                menuPrice(price),
                displayed
        );
    }

    public static MenuGroup menuGroup(String name) {
        return new MenuGroup(new MenuGroupName(name));
    }

    public static MenuName menuName(String name) {
        return new MenuName(name, new FakePurgomalumClient());
    }

    public static MenuPrice menuPrice(BigDecimal price) {
        return new MenuPrice(price);
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
