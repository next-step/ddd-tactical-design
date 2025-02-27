package kitchenpos.menu.tobe.fixture;

import kitchenpos.common.tobe.Profanities;
import kitchenpos.menu.tobe.application.dto.request.MenuCreateRequest;
import kitchenpos.menu.tobe.domain.menu.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class MenuFixture {

    // 기본 값
    private static final String DEFAULT_NAME = "기본 메뉴";
    private static final Long DEFAULT_PRICE = 10000L;
    private static final UUID DEFAULT_MENU_GROUP_ID = UUID.randomUUID();
    private static final boolean DEFAULT_DISPLAY_STATUS = true;

    public static Menu create(Profanities profanities, MenuValidator menuValidator) {
        return create(DEFAULT_NAME, DEFAULT_PRICE, DEFAULT_MENU_GROUP_ID, createDefaultMenuProducts(), DEFAULT_DISPLAY_STATUS, profanities, menuValidator);
    }

    public static Menu create(MenuProducts menuProducts, Profanities profanities, MenuValidator menuValidator) {
        return create(DEFAULT_NAME, DEFAULT_PRICE, DEFAULT_MENU_GROUP_ID, menuProducts, DEFAULT_DISPLAY_STATUS, profanities, menuValidator);
    }
    public static Menu create(UUID menuGroupId, List<MenuProduct> menuProducts, Profanities profanities, MenuValidator menuValidator) {
        return create(DEFAULT_NAME, DEFAULT_PRICE, menuGroupId, menuProducts, DEFAULT_DISPLAY_STATUS, profanities, menuValidator);
    }

    public static Menu create(String name, Long price, UUID menuGroupId, MenuProducts menuProducts, boolean displayed, Profanities profanities, MenuValidator menuValidator) {
        return Menu.of(MenuName.from(name, profanities), MenuPrice.from(price), menuGroupId, MenuDisplayStatus.from(displayed), menuProducts, menuValidator);
    }

    public static Menu create(String name, Long price, UUID menuGroupId, List<MenuProduct> menuProducts, boolean displayed, Profanities profanities, MenuValidator menuValidator) {
        return Menu.of(MenuName.from(name, profanities), MenuPrice.from(price), menuGroupId, MenuDisplayStatus.from(displayed), MenuProducts.from(menuProducts), menuValidator);
    }

    private static List<MenuProduct> createDefaultMenuProducts() {
        return Arrays.asList(new MenuProduct(null, 1, 5000L, UUID.randomUUID()), new MenuProduct(null, 2, 2500L, UUID.randomUUID()));
    }
}