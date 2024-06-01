package kitchenpos;

import kitchenpos.eatinorders.tobe.OrderTable;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuNameValidationService;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductNameValidationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ToBeFixtures {
    public MenuGroup 치킨 = new MenuGroup(UUID.randomUUID(), "치킨");
    public Product 후라이드_20000 = new Product(
            UUID.randomUUID(),
            "후라이드",
            BigDecimal.valueOf(20_000),
            new ProductNameValidationService(new FakePurgomalumClient())
    );
    public Product 양념치킨_20000 = new Product(
            UUID.randomUUID(),
            "양념치킨",
            BigDecimal.valueOf(20_000),
            new ProductNameValidationService(new FakePurgomalumClient())
    );

    public static MenuProduct menuProductOf(long productQuantity, BigDecimal productPrice) {
        MenuProduct menuProduct = new MenuProduct(
                new Random().nextLong(), productQuantity, UUID.randomUUID(), productPrice
        );
        return menuProduct;
    }

    public static Menu menuCreateOf(String name, MenuNameValidationService menuNameValidationService,
                                    BigDecimal price, boolean displayed, List<MenuProduct> menuProducts) {
        Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                menuNameValidationService,
                price,
                displayed,
                menuProducts,
                UUID.randomUUID()
        );
        return menu;
    }

    public static OrderTable emptyOrderTableOf(String name) {
        OrderTable orderTable = new OrderTable(
                UUID.randomUUID(),
                name,
                0,
                false
        );
        return orderTable;
    }

    public static OrderTable sitOrderTableOf(String name) {
        OrderTable orderTable = new OrderTable(
                UUID.randomUUID(),
                name,
                0,
                false
        );
        orderTable.sit();
        return orderTable;
    }
}
