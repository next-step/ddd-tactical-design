package kitchenpos.fixture;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.domain.tobe.menu.DisplayedMenu;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuName;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menu.MenuQuantity;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.ui.dto.MenuCreateRequest;
import kitchenpos.menus.ui.dto.MenuProductCreateRequests;
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
        MenuName menuName = new MenuName("후라이드1+1");
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(30_000L));
        DisplayedMenu displayedMenu = new DisplayedMenu(true);
        MenuProducts menuProducts = new MenuProducts(
                List.of(new MenuProduct(product, new MenuQuantity(2))));

        return new Menu(menuName, menuPrice, menuGroup, displayedMenu, menuProducts);
    }
}
