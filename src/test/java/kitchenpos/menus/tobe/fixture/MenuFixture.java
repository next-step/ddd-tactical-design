package kitchenpos.menus.tobe.fixture;

import kitchenpos.common.tobe.application.FakePurgomalumClient;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Profanities;
import kitchenpos.menus.tobe.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class MenuFixture {
    private static final Profanities profanities = new FakePurgomalumClient();

    public static MenuRequest menuRequest() {
        DisplayedName name = new DisplayedName("양념반후라이드반", profanities);
        Price price = new Price(BigDecimal.valueOf(16_000L));
        boolean displayed = true;
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), new DisplayedName("반반메뉴", profanities));
        MenuProduct menuProduct = MenuProductFixture.menuProduct();
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct));
        return new MenuRequest(name, price, displayed, menuGroup, menuProducts);
    }

    public static Menu menu(MenuRequest menuRequest, MenuCreateValidator validator) {
        return new Menu(menuRequest.getName()
                , menuRequest.getPrice()
                , menuRequest.isDisplayed()
                , menuRequest.getMenuGroup()
                , menuRequest.getMenuProducts(),
                validator);
    }
}
