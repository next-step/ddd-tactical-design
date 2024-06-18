package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class MenuFixture {

    public static Menu createMenuRequest(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        final Menu requestMenu = new Menu();
        requestMenu.setId(UUID.randomUUID());
        requestMenu.setName("후라이드+후라이드");
        requestMenu.setPrice(BigDecimal.valueOf(price));
        requestMenu.setMenuGroup(menuGroup());
        requestMenu.setDisplayed(displayed);
        requestMenu.setMenuProducts(Arrays.asList(menuProducts));
        return requestMenu;
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }


    public static MenuGroup menuGroup(final String name) {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(name);
        return menuGroup;
    }

}
