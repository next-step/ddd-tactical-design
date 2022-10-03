package kitchenpos.menus;

import static kitchenpos.products.ProductFixtures.product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupName;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.domain.Product;

public class MenuFixtures {

    public static UUID INVALID_ID = new UUID(0, 0);

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(String name) {
        return menuGroup(UUID.randomUUID(), name);
    }

    public static MenuGroup menuGroup(UUID id, String name) {
        MenuGroupName menuGroupName = new MenuGroupName(name);
        return new MenuGroup(id, menuGroupName);
    }

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(long price, MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(long price, boolean displayed, MenuProduct... menuProducts) {
        Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName("후라이드+후라이드");
        menu.setPrice(BigDecimal.valueOf(price));
        menu.setMenuGroup(menuGroup());
        menu.setDisplayed(displayed);
        menu.setMenuProducts(Arrays.asList(menuProducts));
        return menu;
    }

    public static MenuProduct menuProduct() {
        MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product());
        menuProduct.setQuantity(2L);
        return menuProduct;
    }

    public static MenuProduct menuProduct(Product product, long quantity) {
        MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }
}
