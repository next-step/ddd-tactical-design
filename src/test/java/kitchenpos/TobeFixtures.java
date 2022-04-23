package kitchenpos;

import java.util.Arrays;
import java.util.List;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

public class TobeFixtures {

    private static final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    public static Product newProduct(String name, long price) {
        return kitchenpos.products.tobe.domain.Product.of(name, price, purgomalumClient);
    }

    public static MenuProduct newMenuProduct(String name, long price) {
        return kitchenpos.menus.tobe.domain.MenuProduct.create(newProduct(name, price), 1L);
    }

    public static MenuProducts menuProducts(MenuProduct... menuProducts) {
        return MenuProducts.from(Arrays.asList(menuProducts));
    }

    public static MenuGroup newMenuGroup(String name) {
        return MenuGroup.create(name, purgomalumClient);
    }

    public static Menu newMenu(String name, Long price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        return Menu.create(name, purgomalumClient, price, menuGroup, true, menuProducts);
    }
}
