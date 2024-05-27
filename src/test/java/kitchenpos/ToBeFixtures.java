package kitchenpos;

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

    public static Product productOf(String name, BigDecimal price, ProductNameValidationService productNameValidationService) {
        Product product = new Product(
                UUID.randomUUID(), name, price, productNameValidationService
        );
        return product;
    }

    public static MenuProduct menuProductOf(Product product, long quantity) {
        MenuProduct menuProduct = new MenuProduct(
                new Random().nextLong(), product, quantity
        );
        return menuProduct;
    }

    public static Menu menuCreateOf(String name, MenuNameValidationService menuNameValidationService,
                                    BigDecimal price, MenuGroup menuGroup, UUID menuGroupId,
                                    boolean displayed, List<MenuProduct> menuProducts) {
        Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                menuNameValidationService,
                price,
                menuGroup,
                menuGroupId,
                displayed,
                menuProducts
        );
        return menu;
    }
}
