package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.products.tobe.domain.fixture.ProductFixture;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuProductsFixture {

    public static MenuProducts MENU_PRODUCTS_FIXTURE() {
        ProductRepository productRepository = new InMemoryProductRepository();
        Product product1 = ProductFixture.PRODUCT_FIXTURE("후라이드", 10000L);
        Product product2 = ProductFixture.PRODUCT_FIXTURE("양념치킨", 10000L);
        productRepository.save(product1);
        productRepository.save(product2);

        MenuProduct menuProduct1 = MenuProductFixture.MENU_PRODUCT_FIXTURE(product1, 10000L, 2L);
        MenuProduct menuProduct2 = MenuProductFixture.MENU_PRODUCT_FIXTURE(product2, 10000L, 2L);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);
        return new MenuProducts(menuProducts, productRepository);
    }

    public static MenuProducts MENU_PRODUCTS_FIXTURE_WITH_NOT_REGISTERED_PRODUCT() {
        ProductRepository productRepository = new InMemoryProductRepository();
        Product product1 = ProductFixture.PRODUCT_FIXTURE("후라이드", 10000L);
        Product product2 = ProductFixture.PRODUCT_FIXTURE("양념치킨", 10000L);

        MenuProduct menuProduct1 = MenuProductFixture.MENU_PRODUCT_FIXTURE(product1, 10000L, 2L);
        MenuProduct menuProduct2 = MenuProductFixture.MENU_PRODUCT_FIXTURE(product2, 10000L, 2L);
        List<MenuProduct> menuProducts = Arrays.asList(menuProduct1, menuProduct2);
        return new MenuProducts(menuProducts, productRepository);
    }

    public static MenuProducts MENU_PRODUCTS_FIXTURE_WITH_PRICE_AND_QUANTITY(Long priceValue, Long quantityValue) {
        ProductRepository productRepository = new InMemoryProductRepository();
        Product product = ProductFixture.PRODUCT_FIXTURE("후라이드", priceValue);
        productRepository.save(product);

        MenuProduct menuProduct = MenuProductFixture.MENU_PRODUCT_FIXTURE(product, priceValue, quantityValue);
        List<MenuProduct> menuProducts = Collections.singletonList(menuProduct);
        return new MenuProducts(menuProducts, productRepository);
    }

}
