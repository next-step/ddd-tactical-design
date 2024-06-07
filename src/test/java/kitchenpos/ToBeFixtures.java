package kitchenpos;

import kitchenpos.menus.application.FakeMenuPurgomalumClient;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.service.MenuNameValidationService;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductNameValidationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ToBeFixtures {
    public Menu 메뉴_치킨 = new kitchenpos.menus.tobe.domain.entity.Menu(
            UUID.randomUUID(),
            "치킨",
            new MenuNameValidationService(new FakeMenuPurgomalumClient()),
            BigDecimal.valueOf(100_000),
            true,
            List.of(new MenuProduct(new Random().nextLong(),5,
                    UUID.randomUUID(), BigDecimal.valueOf(20_000))),
            UUID.randomUUID()
    );

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

    public static Menu menuCreateOf(UUID id, BigDecimal price, boolean displayed) {
        Menu menu = new Menu(
                id,
                "메뉴",
                new MenuNameValidationService(new FakeMenuPurgomalumClient()),
                price,
                displayed,
                List.of(new MenuProduct(new Random().nextLong(),1,
                        UUID.randomUUID(), BigDecimal.valueOf(20_000))),
                UUID.randomUUID()
        );
        return menu;
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
}
