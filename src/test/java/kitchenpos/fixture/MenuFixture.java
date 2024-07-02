package kitchenpos.fixture;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.application.dto.MenuProductCreateRequests;
import kitchenpos.menus.domain.tobe.DisplayedMenu;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuName;
import kitchenpos.menus.domain.tobe.MenuPrice;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.menus.domain.tobe.ProductQuantity;
import kitchenpos.products.domain.tobe.Product;

public class MenuFixture {

    public static MenuCreateRequest createRequest(Long price, MenuGroup menuGroup, Product product,
            Integer quantity) {
        return createRequest("후라이드1+1", price, menuGroup, true, product, quantity);
    }

    public static MenuCreateRequest createRequest(String name, Long price, MenuGroup menuGroup,
            Product product,
            Integer quantity) {
        return createRequest(name, price, menuGroup, true, product, quantity);
    }

    public static MenuCreateRequest createRequest(String name, Long price, MenuGroup menuGroup,
            Boolean displayed, Product product, Integer quantity) {
        MenuName menuName = new MenuName(name);
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(price));
        DisplayedMenu displayedMenu = new DisplayedMenu(displayed);
        MenuProductCreateRequests menuProducts = MenuProductFixture.createRequests(product,
                quantity);

        if (menuGroup != null) {
            return new MenuCreateRequest(menuName, menuPrice, menuGroup.getId(), displayedMenu,
                    menuProducts);
        }

        return new MenuCreateRequest(menuName, menuPrice, null, displayedMenu, menuProducts);
    }

    public static MenuPrice changePriceRequest(Long price) {
        return new MenuPrice(BigDecimal.valueOf(price));
    }

    public static Menu createFriedOnePlusOne(MenuGroup menuGroup, Product product) {
        return createFriedOnePlusOne(menuGroup, product, 30_000L);
    }

    public static Menu createFriedOnePlusOne(MenuGroup menuGroup, Product product, Long price) {
        MenuName menuName = new MenuName("후라이드1+1");
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(price));
        DisplayedMenu displayedMenu = new DisplayedMenu(true);
        MenuProducts menuProducts = new MenuProducts(
            List.of(new MenuProduct(product, new ProductQuantity(2))));

        return new Menu(menuName, menuPrice, menuGroup, displayedMenu, menuProducts);
    }

    public static Menu SeasonedOnePlusOne(MenuGroup menuGroup, Product product) {
        MenuName menuName = new MenuName("양념1+1");
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(35_000L));
        DisplayedMenu displayedMenu = new DisplayedMenu(true);
        MenuProducts menuProducts = new MenuProducts(
            List.of(new MenuProduct(product, new ProductQuantity(2))));

        return new Menu(menuName, menuPrice, menuGroup, displayedMenu, menuProducts);
    }
}
