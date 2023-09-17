package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductPriceChangeEvent;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProductPriceChangeListenerTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuValidator menuValidator;
    private ProductPriceChangeListener productPriceChangeListener;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuValidator = new MenuValidator(productRepository);
        productPriceChangeListener = new ProductPriceChangeListener(menuValidator, menuRepository);
    }

    @Test
    void hideMenuBasedOnMenuAndMenuProductPrice() {
        final Product product = productRepository.save(product("후라이드", 8_000L));
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        final ProductPriceChangeEvent event = new ProductPriceChangeEvent(product.getId());

        productPriceChangeListener.hideMenuBasedOnMenuAndMenuProductPrice(event);

        assertFalse(menu.isDisplayed());
    }
}
