package kitchenpos;

import kitchenpos.menus.application.FakeMenuNameProfanities;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.menus.tobe.intrastructure.ProductClientImpl;
import kitchenpos.products.application.FakeProductNameProfanities;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu(final ProductRepository productRepository) {
        final Product product = product();
        productRepository.save(product);
        return menu(19_000L, true, productRepository, menuProductMaterial(product.getId()));
    }

    public static Menu menu(final boolean displayed, final ProductRepository productRepository) {
        final Product product = product();
        productRepository.save(product);
        return menu(19_000L, displayed, productRepository, menuProductMaterial(product.getId()));
    }

    public static Menu menu(final long price, final boolean displayed, ProductRepository productRepository) {
        final Product product = product();
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId());
        return Menu.from(
                UUID.randomUUID(),
                "후라이드+후라이드", menuNamePolicy(),
                BigDecimal.valueOf(price),
                menuGroup(),
                displayed,
                MenuProducts.from(List.of(menuProductMaterial), new ProductClientImpl(productRepository))
        );
    }

    public static Menu menu(final long price, ProductRepository productRepository, final MenuProductMaterial... menuProductMaterials) {
        return menu(price, false, productRepository, menuProductMaterials);
    }

    public static Menu menu(final long price, final boolean displayed, ProductRepository productRepository, final MenuProductMaterial... menuProductMaterials) {
        final Product product = product();
        productRepository.save(product);
        return Menu.from(
                UUID.randomUUID(),
                "후라이드+후라이드",
                menuNamePolicy(),
                BigDecimal.valueOf(price),
                menuGroup(),
                displayed,
                MenuProducts.from(List.of(menuProductMaterials), new ProductClientImpl(productRepository))
        );
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), new MenuGroupName(name));
    }

    public static MenuProductMaterial menuProductMaterial() {
        return menuProductMaterial(UUID.randomUUID(), 2L);
    }

    public static MenuProductMaterial menuProductMaterial(UUID productId) {
        return menuProductMaterial(productId, 2L);
    }

    public static MenuProductMaterial menuProductMaterial(UUID productId, long quantity) {
        return new MenuProductMaterial(productId, quantity);
    }

    public static MenuProduct menuProduct() {
        final Product product = product();
        final InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productRepository.save(product);
        return MenuProduct.from(
                product.getId(),
                2L,
                new ProductClientImpl(productRepository)
        );
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productRepository.save(product);
        return MenuProduct.from(
                product.getId(),
                quantity,
                new ProductClientImpl(productRepository)
        );
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.from(UUID.randomUUID(), name, BigDecimal.valueOf(price), productNamePolicy());
    }

    public static ProductNamePolicy productNamePolicy() {
        return new ProductNamePolicy(new FakeProductNameProfanities());
    }

    public static MenuNamePolicy menuNamePolicy() {
        return new MenuNamePolicy(new FakeMenuNameProfanities());
    }
}
