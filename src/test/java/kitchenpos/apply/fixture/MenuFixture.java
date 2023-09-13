package kitchenpos.apply.fixture;

import kitchenpos.apply.menu.tobe.infra.FakeMenuPriceChecker;
import kitchenpos.apply.menus.tobe.domain.*;
import kitchenpos.support.infra.DefaultPurgomalumClient;
import kitchenpos.apply.menus.tobe.ui.MenuGroupRequest;
import kitchenpos.apply.menus.tobe.ui.MenuProductRequest;
import kitchenpos.apply.menus.tobe.ui.MenuRequest;
import kitchenpos.apply.products.tobe.domain.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.apply.fixture.TobeFixtures.product;

public class MenuFixture {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, true, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProductList) {
        MenuName menuName = new MenuName("후라이드+후라이드", new DefaultPurgomalumClient(new RestTemplateBuilder()));
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProductList));
        return Menu.of(menuName, BigDecimal.valueOf(price), menuGroup(), displayed, menuProducts, new FakeMenuPriceChecker());
    }

    public static MenuRequest menuRequest(final long price, final MenuProductRequest... menuProducts) {
        return menuRequest(price, true, menuProducts);
    }

    public static MenuRequest menuRequest(final BigDecimal price, final MenuProductRequest... menuProductRequests) {
        UUID menuGroupId = UUID.fromString(menuGroup().getId());
        return  new MenuRequest("후라이드+후라이드", price, menuGroupId, true, List.of(menuProductRequests));
    }

    public static MenuRequest menuRequest(final long price, final boolean displayed, final MenuProductRequest... menuProductRequests) {
        UUID menuGroupId = UUID.fromString(menuGroup().getId());
        return new MenuRequest("후라이드+후라이드", BigDecimal.valueOf(price), menuGroupId, displayed, List.of(menuProductRequests));
    }

    public static MenuRequest menuRequest(final String name, final long price, UUID menuGroupId, final boolean displayed, final MenuProductRequest... menuProductRequests) {
        return new MenuRequest(name, BigDecimal.valueOf(price), menuGroupId, displayed, List.of(menuProductRequests));
    }

    public static MenuRequest menuRequest(final String name, final BigDecimal price, UUID menuGroupId, final boolean displayed, final MenuProductRequest... menuProductRequests) {
        return new MenuRequest(name, price, menuGroupId, displayed, List.of(menuProductRequests));
    }

    public static MenuRequest menuRequest(final String name, final BigDecimal price, UUID menuGroupId
            , final boolean displayed, final List<MenuProductRequest> menuProductRequests) {
        return new MenuRequest(name, price, menuGroupId, displayed, menuProductRequests);
    }


    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return MenuGroup.of(name);
    }

    public static MenuGroupRequest menuGroupRequest() {
        return menuGroupRequest("두마리메뉴");
    }

    public static MenuGroupRequest menuGroupRequest(final String name) {
        return new MenuGroupRequest(name);
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(new Random().nextLong(), UUID.fromString(product().getId()), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(new Random().nextLong(), UUID.fromString(product.getId()), quantity);
    }

    public static MenuProductRequest menuProductRequest(final UUID productId, final long quantity) {
        return new MenuProductRequest(new Random().nextLong(), productId, quantity, UUID.fromString(menu().getId()));
    }

    public static MenuProductRequest menuProductRequest(final Product product, final long quantity) {
        return new MenuProductRequest(new Random().nextLong(), UUID.fromString(product.getId()), quantity, UUID.fromString(menu().getId()));
    }
}
