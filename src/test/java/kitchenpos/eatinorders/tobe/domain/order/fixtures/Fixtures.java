package kitchenpos.eatinorders.tobe.domain.order.fixtures;

import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.DisplayedNamePolicy;
import kitchenpos.common.domain.FakeDisplayedNamePolicy;
import kitchenpos.common.domain.Money;
import kitchenpos.eatinorders.tobe.domain.order.menu.Menu;
import kitchenpos.eatinorders.tobe.domain.order.menu.MenuProduct;
import kitchenpos.eatinorders.tobe.domain.order.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuProductId;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupId;
import kitchenpos.products.tobe.domain.ProductId;

public class Fixtures {

    private static final DisplayedNamePolicy POLICY = new FakeDisplayedNamePolicy();

    private static final MenuProduct MENU_PRODUCT = new MenuProduct(
        new MenuProductId(1L),
        new ProductId(UUID.randomUUID()),
        new Money(1_000),
        2L
    );

    private static final MenuProducts MENU_PRODUCTS = new MenuProducts(MENU_PRODUCT);

    public static final Menu MENU = new Menu(
        new MenuId(UUID.randomUUID()),
        new DisplayedName("세트1번", POLICY),
        new Money(1_800),
        new MenuGroupId(UUID.randomUUID()),
        MENU_PRODUCTS
    );
}
