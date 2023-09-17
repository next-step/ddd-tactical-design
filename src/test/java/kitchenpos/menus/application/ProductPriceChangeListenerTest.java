package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.InMemoryProductRepository;
import kitchenpos.products.event.ProductPriceChangeEvent;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceChangeListenerTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private ProductPriceChangeListener productPriceChangeListener;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        productPriceChangeListener = new ProductPriceChangeListener(menuRepository);
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
