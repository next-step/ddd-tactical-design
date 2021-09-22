package kitchenpos.fixture;

import static kitchenpos.fixture.MenuProductsFixture.MENU_PRODUCTS;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.domain.model.MenuPrice;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;

public class MenuFixture {

    private static Profanities profanities = new FakeProfanities();

    public static Menu MENU1() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(10000L), menuGroup, true, MENU_PRODUCTS());
    }

    public static Menu NOT_DISPLAYED_MENU() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(10000L), menuGroup, false, MENU_PRODUCTS());
    }

    public static Menu MENU_WITH_PRICE(final long price) {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(price), menuGroup, true, MENU_PRODUCTS());
    }

    public static Menu NOT_DISPLAYED_MENU_WITH_PRICE(final long price) {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(price), menuGroup, false, MENU_PRODUCTS());
    }


}
