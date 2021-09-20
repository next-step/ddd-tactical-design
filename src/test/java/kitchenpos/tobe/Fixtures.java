package kitchenpos.tobe;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.service.MenuDomainService;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.service.ProductDomainService;
import kitchenpos.tobe.menus.application.InMemoryMenuGroupRepository;
import kitchenpos.tobe.menus.application.FakeProductRepository;
import kitchenpos.tobe.products.application.FakePurgomalumClient;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);
    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        ProductDomainService productDomainService = new ProductDomainService(new FakePurgomalumClient());
        return new Product(name, BigDecimal.valueOf(price), productDomainService);
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        final MenuGroup menuGroup = new MenuGroup(name);
        return menuGroup;
    }

    public static Menu menu(UUID menuGroupId, final MenuDomainService menuDomainService) {
        return menu(19_000L, true, menuGroupId, menuDomainService, menuProduct());
    }

    public static Menu menu(final long price, UUID menuGroupId, final MenuDomainService menuDomainService, final MenuProduct... menuProducts) {
        return menu(price, false, menuGroupId, menuDomainService, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, UUID menuGroupId, final MenuDomainService menuDomainService, final MenuProduct... menuProducts) {

        final Menu menu = new Menu("후라이드+후라이드",
                BigDecimal.valueOf(price),
                displayed,
                menuGroupId,
                Arrays.asList(menuProducts),
                menuDomainService);

        return menu;
    }

    public static MenuProduct menuProduct() {
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 2L);
        return menuProduct;
    }

    public static MenuProduct menuProduct(final kitchenpos.menus.tobe.domain.model.Product product, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct(product.getId(), quantity);
        return menuProduct;
    }


    public static kitchenpos.menus.tobe.domain.model.Product product(final UUID id, final String name, final long price) {
        return new kitchenpos.menus.tobe.domain.model.Product(id, name, BigDecimal.valueOf(price));
    }
}
