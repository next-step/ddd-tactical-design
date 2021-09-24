package kitchenpos.fixture;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.Quantity;

import java.util.UUID;

public class MenuFixture {
    public static MenuGroup 메뉴그룹() {
        return 메뉴그룹("메뉴그룹");
    }

    public static MenuGroup 메뉴그룹(final String name) {
        return new MenuGroup(name);
    }

    public static MenuGroup 메뉴그룹(final UUID id, final String name) {
        return new MenuGroup(id, name);
    }

    public static MenuProduct 메뉴상품() {
        return 메뉴상품(UUID.randomUUID(), 2L);
    }

    public static MenuProduct 메뉴상품(final UUID productId) {
        return 메뉴상품(productId, 2L);
    }

    public static MenuProduct 메뉴상품(final UUID productId, final long quantity) {
        return new MenuProduct(productId, new Quantity(quantity));
    }
}
