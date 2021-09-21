package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;

import java.util.Collections;
import java.util.UUID;

public class MenuFixture {

    public static Menu MENU_WITH_ID_AND_PRICE(final UUID id, final Price price) {
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), price, new Quantity(1L));
        final MenuProducts menuProducts = new MenuProducts(Collections.singletonList(menuProduct));

        return new kitchenpos.menus.tobe.domain.model.Menu(
                id,
                new DisplayedName("후라이드치킨"),
                price,
                menuProducts,
                UUID.randomUUID(),
                true,
                menu -> {
                }
        );
    }

    public static Menu MENU_WITH_PRICE(final Price price) {
        return MENU_WITH_ID_AND_PRICE(UUID.randomUUID(), price);
    }
}
