package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.List;

import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;

public class MenuFixture {
    public static ToBeMenuGroup createMenuGroup(String name) {
        return new ToBeMenuGroup(name);
    }

    public static ToBeProduct createProduct(final String name, final long price) {
        return new ToBeProduct(name, BigDecimal.valueOf(price), false);
    }

    public static ToBeMenuProduct createMenuProduct(long price, long quantity) {
        return createMenuProduct(createProduct("김밥", price), quantity);
    }

    public static ToBeMenuProduct createMenuProduct(final ToBeProduct product, final long quantity) {
        return new ToBeMenuProduct(product, quantity);
    }

    public static ToBeMenu createMenuForNullMenuGroup(final String name, final long price) {
        return createMenu(name,
            price,
            null,
            true,
            false,
            createMenuProduct(createProduct("김밥", price), 1)
        );
    }

    public static ToBeMenu createMenuForNullProduct(final String name, final long price) {
        return createMenu(name,
            price,
            createMenuGroup("특가"),
            true,
            false,
            List.of()
        );
    }

    public static ToBeMenu createMenu(final String name, final long price, final ToBeMenuGroup menuGroup,
        boolean displayed, final boolean containsProfanity, final ToBeMenuProduct... menuProducts) {
        return createMenu(name,
            price,
            menuGroup,
            displayed,
            containsProfanity,
            List.of(menuProducts)
        );
    }

    public static ToBeMenu createMenu(final String name, final long price, final ToBeMenuGroup menuGroup,
        boolean displayed, final boolean containsProfanity, final List<ToBeMenuProduct> menuProducts) {
        return new ToBeMenu(name,
            BigDecimal.valueOf(price),
            menuGroup,
            displayed,
            containsProfanity,
            menuProducts
        );
    }
}
