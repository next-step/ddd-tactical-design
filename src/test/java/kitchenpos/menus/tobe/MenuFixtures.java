package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.menuGroup.domain.MenuGroup;
import org.springframework.test.util.ReflectionTestUtils;

public class MenuFixtures {
    public static final Long TWO_FRIED_CHICKENS_ID = 1L;
    public static final Long TWO_CHICKENS_ID = 1L;
    public static final Long FRIED_CHICKEN_ID = 1L;

//    public static Menu twoFriedChickens() {
//        final Menu menu = new Menu();
//        menu.setId(TWO_FRIED_CHICKENS_ID);
//        menu.setName("후라이드+후라이드");
//        menu.setPrice(BigDecimal.valueOf(19_000L));
//        menu.setMenuGroupId(TWO_CHICKENS_ID);
//        menu.setMenuProducts(Arrays.asList(menuProduct()));
//        return menu;
//    }

    public static MenuGroup twoChickens() {
        final MenuGroup menuGroup = new MenuGroup("두마리메뉴");
        ReflectionTestUtils.setField(menuGroup, "id", TWO_CHICKENS_ID);
        return menuGroup;
    }

//    private static MenuProduct menuProduct() {
//        final MenuProduct menuProduct = new MenuProduct();
//        menuProduct.setMenuId(TWO_FRIED_CHICKENS_ID);
//        menuProduct.setProductId(FRIED_CHICKEN_ID);
//        menuProduct.setQuantity(2);
//        return menuProduct;
//    }
}
