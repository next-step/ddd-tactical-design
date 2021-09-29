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

    public static Menu MENU_WITH_PRICE(final Price price) {
        return new Menu(
                UUID.randomUUID(),
                new DisplayedName("후라이드치킨"),
                price,
                new MenuProducts(Collections.singletonList(
                        new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), price, new Quantity(1L))
                )),
                UUID.randomUUID(),
                true,
                dummy -> {
                }
        );
    }

    public static Menu NOT_DISPLAYED_MENU() {
        return new Menu(
                UUID.randomUUID(),
                new DisplayedName("후라이드치킨"),
                new Price(16_000L),
                new MenuProducts(Collections.singletonList(
                        new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), new Price(16_000L), new Quantity(1L))
                )),
                UUID.randomUUID(),
                false,
                dummy -> {
                }
        );
    }
}
