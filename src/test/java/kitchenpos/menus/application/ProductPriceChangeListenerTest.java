package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceChangeEventProduct;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;


class ProductPriceChangeListenerTest {

    private ProductPriceChangeListener productPriceChangeListener;
    private MenuRepository menuRepository;
    private Product product;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        productPriceChangeListener = new ProductPriceChangeListener(menuRepository);
        productRepository = new InMemoryProductRepository();
        product = productRepository.save(product("후라이드", 16_000L));
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Menu menu = menuRepository.save(menu(19_000L, menuProduct(product, 2L)));
        productPriceChangeListener.listen(new ProductPriceChangeEventProduct(product.getId()));
        assertThat(menu.isDisplayed()).isFalse();
    }

}
