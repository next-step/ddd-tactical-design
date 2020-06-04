package kitchenpos.menus;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.menus.model.MenuCreateRequest;
import kitchenpos.menus.model.MenuGroupCreateRequest;
import kitchenpos.menus.tobe.domain.group.MenuGroup;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;

public class Fixtures {

    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long TWO_CHICKENS_ID = 1L;
    public static final Long FRIED_CHICKEN_ID = 1L;

    public static Menu twoFriedChickens() {
        return Menu.of(TWO_FRIED_CHICKENS_ID, "후라이드+후라이드", BigDecimal.valueOf(19_000L),
            TWO_CHICKENS_ID, new MenuProducts(Arrays.asList(menuProduct())));
    }

    public static MenuCreateRequest twoFriedChickensRequest() {
        final MenuCreateRequest menu = new MenuCreateRequest();
        menu.setName("후라이드+후라이드");
        menu.setPrice(BigDecimal.valueOf(19_000L));
        menu.setMenuGroupId(TWO_CHICKENS_ID);
        menu.setMenuProducts(Arrays.asList(menuProductRequest()));
        return menu;
    }

    public static MenuGroupCreateRequest twoChickensRequest() {
        final MenuGroupCreateRequest menuGroup = new MenuGroupCreateRequest();
        menuGroup.setName("두마리메뉴");
        return menuGroup;
    }

    public static MenuGroup twoChickens() {
        final MenuGroup menuGroup = new MenuGroup(TWO_CHICKENS_ID, "두마리메뉴");
        return menuGroup;
    }


    private static MenuCreateRequest.MenuProduct menuProductRequest() {
        final MenuCreateRequest.MenuProduct menuProduct = new MenuCreateRequest.MenuProduct();
        menuProduct.setProductId(FRIED_CHICKEN_ID);
        menuProduct.setQuantity(2);
        return menuProduct;
    }

    private static MenuProduct menuProduct() {
        return new MenuProduct(FRIED_CHICKEN_ID, 2, BigDecimal.valueOf(10000));
    }
}
