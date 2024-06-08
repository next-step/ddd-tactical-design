package kitchenpos.fixture;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.support.domain.MenuPrice;
import kitchenpos.support.domain.ProductPrice;

import java.util.Arrays;
import java.util.Random;

import static kitchenpos.fixture.ProductFixture.product;

public class MenuFixture {
    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return new Menu(
                MenuName.from("후라이드+후라이드"),
                MenuPrice.from(price),
                menuGroup(),
                displayed,
                MenuProducts.from(Arrays.asList(menuProducts))
        );
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return MenuGroup.from(MenuGroupName.from(name));
    }

    public static MenuProduct menuProduct() {
        return menuProduct(product(), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(new Random().nextLong(), product.getId(), ProductPrice.from(product.getPrice()), quantity);
    }
}
