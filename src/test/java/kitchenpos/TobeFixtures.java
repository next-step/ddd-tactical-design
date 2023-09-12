package kitchenpos;


import kitchenpos.menus.tobe.application.DefaultMenuPriceChecker;
import kitchenpos.menus.tobe.application.FakeMenuPriceChecker;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.infra.DefaultPurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class TobeFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProductList) {
        MenuName menuName = new MenuName("후라이드+후라이드", new DefaultPurgomalumClient(new RestTemplateBuilder()));
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProductList));
        return Menu.of(menuName, BigDecimal.valueOf(price), menuGroup(), displayed, menuProducts, new FakeMenuPriceChecker());
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return MenuGroup.of(name);
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(new Random().nextLong(), UUID.fromString(product().getId()), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(new Random().nextLong(), UUID.fromString(product.getId()), quantity);
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        ProductName productName = new ProductName(name);
        return Product.of(productName, BigDecimal.valueOf(price));
    }
}
