package kitchenpos.fixture;

import static kitchenpos.fixture.MenuProductsFixture.MENU_PRODUCTS;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.menu.domain.model.MenuPrice;
import kitchenpos.menus.tobe.menu.domain.model.Menu;
import kitchenpos.menus.tobe.menugroup.domain.model.MenuGroupV2;

public class MenuFixture {

    private static final Profanities profanities = new FakeProfanities();

    public static Menu MENU1() {
        final MenuGroupV2 menuGroup = new MenuGroupV2(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(10000L), menuGroup.getId(), true, MENU_PRODUCTS());
    }

    public static Menu NOT_DISPLAYED_MENU() {
        final MenuGroupV2 menuGroup = new MenuGroupV2(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(10000L), menuGroup.getId(), false, MENU_PRODUCTS());
    }

    public static Menu MENU_WITH_PRICE(final long price) {
        final MenuGroupV2 menuGroup = new MenuGroupV2(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(price), menuGroup.getId(), true, MENU_PRODUCTS());
    }

    public static Menu NOT_DISPLAYED_MENU_WITH_PRICE(final long price) {
        final MenuGroupV2 menuGroup = new MenuGroupV2(new DisplayedName("메뉴그룹1", profanities));
        return new Menu(new DisplayedName("메뉴1", profanities), new MenuPrice(price), menuGroup.getId(), false, MENU_PRODUCTS());
    }


}
