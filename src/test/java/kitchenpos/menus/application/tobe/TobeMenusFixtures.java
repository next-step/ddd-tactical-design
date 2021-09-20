package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TobeMenusFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final Long price) {
        ProductName productName = new ProductName(name);
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new TobeProduct(UUID.randomUUID(), productName, productPrice);
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

//    public static TobeMenu menu() {
////        TobeMenu(UUID id,
////                MenuName name,
////                MenuPrice menuPrice,
////                TobeMenuGroup menuGroup,
////                boolean displayed,
////                MenuProducts menuProducts) {
//
//    }
}
