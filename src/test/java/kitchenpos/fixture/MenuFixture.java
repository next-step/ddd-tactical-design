package kitchenpos.fixture;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.menus.tobe.domain.*;

import java.util.Arrays;
import java.util.List;
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

    public static Menu 메뉴() {
        return 메뉴("메뉴이름", 1000L, 메뉴그룹(), Arrays.asList(메뉴상품(), 메뉴상품()));
    }

    public static Menu 메뉴(final String name, final long amount, final MenuGroup menuGroup, final List<MenuProduct> menuProducts) {
        return new Menu(new Name(name, new FakeProfanities()), new Amount(amount), menuGroup, menuProducts);
    }
}
