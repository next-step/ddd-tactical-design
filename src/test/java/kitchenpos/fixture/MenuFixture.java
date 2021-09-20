package kitchenpos.fixture;

import static kitchenpos.fixture.MenuProductsFixture.MENU_PRODUCTS;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;

public class MenuFixture {

    private static PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    public static Menu MENU1() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        return new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(10000L), menuGroup, true, MENU_PRODUCTS());
    }

    public static Menu NOT_DISPLAYED_MENU() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        return new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(10000L), menuGroup, false, MENU_PRODUCTS());
    }

    public static Menu MENU_WITH_PRICE(final long price) {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        return new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(price), menuGroup, true, MENU_PRODUCTS());
    }

    public static Menu NOT_DISPLAYED_MENU_WITH_PRICE(final long price) {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        return new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(price), menuGroup, false, MENU_PRODUCTS());
    }


}
