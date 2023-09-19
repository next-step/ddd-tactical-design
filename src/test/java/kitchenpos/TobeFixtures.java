package kitchenpos;

import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.menus.tobe.application.FakeMenuPurgomalumChecker;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupName;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProductQuantity;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProduct;
import kitchenpos.products.application.FakePurgomalumChecker;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.TobeProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class TobeFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static TobeMenu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static TobeMenu menu(final long price, final TobeMenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static TobeMenu menu(final long price, final boolean displayed, final TobeMenuProduct... menuProducts) {
        return new TobeMenu(UUID.randomUUID(), new MenuName("후라이드+후라이드", new FakeMenuPurgomalumChecker()),
                            new MenuPrice(BigDecimal.valueOf(price)), menuGroup().getId(), displayed,
                            Arrays.asList(menuProducts));
    }

    public static TobeMenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static TobeMenuGroup menuGroup(final String name) {
        return new TobeMenuGroup(UUID.randomUUID(), new TobeMenuGroupName(name));
    }

    public static TobeMenuProduct menuProduct() {
        return new TobeMenuProduct(new Random().nextLong(), product().getId(),
                                   new MenuPrice(product().getBigDecimalPrice()), new TobeMenuProductQuantity(2L)
        );
    }

    public static TobeMenuProduct menuProduct(final TobeProduct product, final long quantity) {
        return new TobeMenuProduct(new Random().nextLong(), product.getId(),
                                   new MenuPrice(product().getBigDecimalPrice()), new TobeMenuProductQuantity(quantity)
        );
    }

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final long price) {
        return new TobeProduct(UUID.randomUUID(), new ProductName(name, new FakePurgomalumChecker()), new ProductPrice(BigDecimal.valueOf(price)));
    }
}
