package kitchenpos.global;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

public final class TobeFixtures {

    private TobeFixtures() {
    }

    public static Name name(String value) {
        return new Name(value, new FakePurgomalumClient());
    }

    public static Price price(BigDecimal value) {
        return new Price(value);
    }

    public static Quantity quantity(long value) {
        return new Quantity(value);
    }

    public static Product product(String name) {
        return product(name, BigDecimal.ZERO);
    }

    public static Product product(String name, BigDecimal price) {
        return new Product(name(name), price(price));
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
                name(" 후라이드+후라이드"),
                price(menuPrice),
                menuGroup("두마리 치킨"),
                false,
                menuProducts(menuProduct)
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
                name(name),
                menuGroup(groupName),
                menuProducts,
                price(price),
                displayed
        );
    }

    public static MenuGroup menuGroup(String name) {
        return new MenuGroup(name(name));
    }

    public static MenuProduct menuProduct(long quantity, BigDecimal productPrice) {
        return new MenuProduct(
                UUID.randomUUID(),
                UUID.randomUUID(),
                quantity(quantity),
                price(productPrice)
        );
    }

    public static MenuProducts menuProducts(MenuProduct... menuProducts) {
        return new MenuProducts(List.of(menuProducts));
    }
}
