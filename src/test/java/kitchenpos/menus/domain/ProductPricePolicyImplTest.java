package kitchenpos.menus.domain;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductPricePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductPricePolicyImplTest {
    private MenuRepository menuRepository;
    private ProductRepository productRepository;
    private ProductPricePolicy productPricePolicy;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        productPricePolicy = new ProductPricePolicyImpl(productRepository, menuRepository);
    }

    @DisplayName("메뉴 상품의 가격 변경한 후 (메뉴의 가격 <= 메뉴 상품들의 총 가격) 이라면 가격이 변경되고 메뉴 노출 상태는 유지된다.")
    @ParameterizedTest
    @ValueSource(longs = {15_000L, 15_001L})
    void changeMenuProductPriceWithEqualsMenuPriceAndMenuProductTotalPrice(long changePrice) {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final Menu menu = menuRepository.save(menu(15_000L, true, menuProduct(product, 1L)));
        product.changePrice(ProductPrice.from(BigDecimal.valueOf(changePrice)));
        productPricePolicy.changePrice(product, ProductPrice.from(BigDecimal.valueOf(changePrice)));
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isTrue();
    }

    @DisplayName("메뉴 상품의 가격 변경한 후 (메뉴의 가격 > 메뉴 상품들의 총 가격) 이라면 메뉴상품 가격은 변경되고 해당 메뉴는 숨겨진 메뉴로 변경된다")
    @Test
    void changeMenuProductPriceWithMenuPriceBiggerThanMenuProductTotalPrice() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final Menu menu = menuRepository.save(menu(15_000L, true, menuProduct(product, 1L)));
        product.changePrice(ProductPrice.from(BigDecimal.valueOf(14_999L)));
        productPricePolicy.changePrice(product, ProductPrice.from(BigDecimal.valueOf(14_999L)));
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }
}
