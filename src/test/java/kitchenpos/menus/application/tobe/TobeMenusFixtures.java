package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.ui.MenuForm;
import kitchenpos.menus.tobe.ui.MenuProductForm;
import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TobeMenusFixtures {
    public static final TobePurgomalumClient purgomalumClient = new TobeFakePurgomalumClient();
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final Long price) {
        ProductName productName = new ProductName(name);
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new TobeProduct(INVALID_ID, productName, productPrice);
    }

    public static TobeMenuProduct menuProduct() {
        return new TobeMenuProduct(product(), 0);
    }

    public static TobeMenuProduct menuProduct(Long quantity) {
        return new TobeMenuProduct(product(), quantity);
    }

    public static TobeMenuProduct menuProduct(TobeProduct product, Long quantity) {
        return new TobeMenuProduct(product, quantity);
    }

    public static MenuProducts menuProducts() {
        List<TobeMenuProduct> menuProducts = new ArrayList<>();
        menuProducts.add(menuProduct(product("후라이드", 16_000L), 1L));
        menuProducts.add(menuProduct(product("양념", 17_000L), 1L));
        return menuProducts(menuProducts);
    }

    public static MenuProducts menuProducts(List<TobeMenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    public static TobeMenu menu() {
        MenuName menuName = new MenuName("두마리 세트", purgomalumClient);
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(33_000L));
        TobeMenuGroup menuGroup = tobeMenuGroup();
        return new TobeMenu(
                    UUID.randomUUID(),
                    menuName,
                    menuPrice,
                    menuGroup,
                    true,
                    menuProducts()
                );
    }

    public static MenuForm menuForm() {
        return MenuForm.of(menu());
    }

    public static TobeMenuGroup tobeMenuGroup() {
        MenuGroupName menuGroupName = new MenuGroupName("세트 메뉴", purgomalumClient);
        TobeMenuGroup menuGroup = new TobeMenuGroup(menuGroupName);
        return menuGroup;
    }
}
