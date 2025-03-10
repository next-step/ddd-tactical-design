package kitchenpos.mock.fixture;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProduct;
import kitchenpos.menus.tobe.dto.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.dto.menu.MenuProductRequest;
import kitchenpos.mock.TestUtil;
import kitchenpos.mock.adapter.FakeProductPriceAdapter;
import kitchenpos.mock.client.FakePurgomalumClient;

public class MenuFixture {

    public static final String MENU_NAME = "간장 치킨";
    private static final FakePurgomalumClient purgomalumClient = new FakePurgomalumClient();

    public static Menu create(String name,
        BigDecimal price,
        MenuGroup menuGroup,
        List<MenuProduct> menuProducts,
        FakeProductPriceAdapter productPriceAdapter) {

        return new Menu(
            UUID.randomUUID(),
            name,
            purgomalumClient,
            price,
            menuGroup.getId(),
            true,
            menuProducts,
            productPriceAdapter);
    }

    public static MenuCreateRequest createCreateRequest(UUID menuGroupId,
        List<MenuProduct> menuProducts) {

        return new MenuCreateRequest(
            MENU_NAME,
            TestUtil.PRICE,
            menuGroupId,
            toMenuProductRequests(menuProducts),
            true);
    }

    public static MenuCreateRequest toRequest(Menu menu) {

        return new MenuCreateRequest(
            menu.getName(),
            menu.getPrice(),
            menu.getMenuGroupId(),
            toMenuProductRequests(menu.getMenuProducts()),
            menu.isDisplayed());
    }

    private static List<MenuProductRequest> toMenuProductRequests(List<MenuProduct> menuProducts) {

        if (menuProducts == null || menuProducts.isEmpty()) {
            return null;
        }
        return menuProducts.stream()
            .map(x -> new MenuProductRequest(x.getProductId(), x.getQuantity())).toList();
    }
}
