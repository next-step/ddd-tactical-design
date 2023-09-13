package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProducts;

public class MenuFixture {

    public static ToBeMenuProduct createMenuProduct(long price, long quantity) {
        return new ToBeMenuProduct(UUID.randomUUID(), price, quantity);
    }

    public static ToBeMenu createMenuForNullMenuGroup(final String name, final long price) {
        return createMenu(name,
            price,
            null,
            true,
            false,
            createMenuProduct(8000, 1)
        );
    }

    public static ToBeMenu createMenuForNullProduct(final String name, final long price) {
        return createMenu(name,
            price,
            UUID.randomUUID(),
            true,
            false,
            List.of()
        );
    }

    public static ToBeMenu createMenu(final String name, final long price, final UUID menuGroupId,
        boolean displayed, final boolean containsProfanity, final ToBeMenuProduct... menuProducts) {
        return createMenu(name,
            price,
            menuGroupId,
            displayed,
            containsProfanity,
            List.of(menuProducts)
        );
    }

    public static ToBeMenu createMenu(final String name, final long price, final UUID menuGroupId,
        boolean displayed, final boolean containsProfanity, final List<ToBeMenuProduct> menuProducts) {
        return new ToBeMenu(name,
            BigDecimal.valueOf(price),
            menuGroupId,
            displayed,
            containsProfanity,
            new ToBeMenuProducts(menuProducts)
        );
    }
}
