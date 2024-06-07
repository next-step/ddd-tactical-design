package kitchenpos.products.tobe.application;


import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.changePriceRequest;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

class ProductCommandHandlerTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        this.productRepository = new InMemoryProductRepository();
        this.menuRepository = new InMemoryMenuRepository();
    }


    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        // given
        final Product product = productRepository.save(product("후라이드", 16_000L));
        BigDecimal changedPrice = BigDecimal.valueOf(8_000L);
        ProductCommandHandler productCommandHandler = new ProductCommandHandler((req) -> null, (id, req) -> {
            product.changePrice(ProductPrice.of(changedPrice));
            return product;
        }, menuRepository);
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));

        // when
        productCommandHandler.changePrice(product.getId(), changePriceRequest(changedPrice));

        // then
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }
}
