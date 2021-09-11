package kitchenpos.fixture;

import static kitchenpos.fixture.ProductFixture.CHEAP_PRODUCT;
import static kitchenpos.fixture.ProductFixture.PRODUCT1;
import static kitchenpos.fixture.ProductFixture.PRODUCT2;

import java.util.UUID;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.Quantity;
import kitchenpos.products.tobe.domain.model.Product;

public class MenuProductFixture {

    private static final Quantity TWO = new Quantity(2L);
    private static final Quantity THREE = new Quantity(3L);

    public static MenuProduct MENU_PRODUCT1() {
        return new MenuProduct(PRODUCT1().getId(), TWO);
    }

    public static MenuProduct MENU_PRODUCT2() {
        return new MenuProduct(PRODUCT2().getId(), THREE);
    }

    public static MenuProduct WRONG_PRODUCT() {
        return new MenuProduct(UUID.randomUUID(), THREE);
    }

    public static MenuProduct CHEAP_MENU_PRODUCT() {
        final Product cheapProduct = CHEAP_PRODUCT();
        return new MenuProduct(cheapProduct.getId(), TWO);
    }

//    public static List<MenuProduct> MENU_PRODUCTS() {
//        return Arrays.asList(MENU_PRODUCT1(), MENU_PRODUCT2());
//    }
//
//    public static List<MenuProduct> WRONG_PRODUCTS() {
//        return Arrays.asList(MENU_PRODUCT1(), WRONG_PRODUCT());
//    }
//
//    public static List<MenuProduct> CHEAP_MENU_PRODUCTS() {
//        return Arrays.asList(CHEAP_MENU_PRODUCT(), CHEAP_MENU_PRODUCT());
//    }
//
//    public static List<MenuProduct> QUANTITY_NAGATIVE_MENU_PRODUCTS() {
//        return Arrays.asList(MENU_PRODUCT1(), NEGATIVE_QUANTITY_MENU_PRODUCT());
//    }

}
