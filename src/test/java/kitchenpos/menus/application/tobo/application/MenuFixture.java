package kitchenpos.menus.application.tobo.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.menus.application.tobo.application.MenuGroupFixture.createMenuGroupRequest;

public class MenuFixture {
    public static Menu createMenuRequest(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        final Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName("후라이드+후라이드");
        menu.setPrice(BigDecimal.valueOf(price));
        menu.setMenuGroup(createMenuGroupRequest());
        menu.setDisplayed(displayed);
        menu.setMenuProducts(Arrays.asList(menuProducts));
        return menu;
    }

}
