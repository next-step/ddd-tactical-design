package kitchenpos.menus.application;

import kitchenpos.menus.domain.*;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.dto.ProductPriceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menuGroup;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MenuServiceIntegrationTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private MenuGroupRepository menuGroupRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Nested
    @DisplayName("상품의 가격을 변경할 때")
    class PriceChangeTest {

        @DisplayName("상품의 가격을 변경할 수 있다.")
        @Test
        void changePrice() {
            final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
            final ProductPriceRequest expected = changePriceRequest(15_000L);
            final Product actual = productService.changePrice(productId, expected);
            assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        }

        @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
        @ValueSource(strings = "-1000")
        @NullSource
        @ParameterizedTest
        void changePrice(final BigDecimal price) {
            final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
            final ProductPriceRequest expected = changePriceRequest(price);
            assertThatThrownBy(() -> productService.changePrice(productId, expected))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
        @Test
        void changePriceInMenu() {
            final Product product = productRepository.save(product("후라이드", 16_000L));
            MenuGroup menuGroup = menuGroupRepository.save(menuGroup());
            Menu menu = menuRepository.save(new Menu(UUID.randomUUID(), "후라이드 세트", BigDecimal.valueOf(19000), menuGroup, true, List.of(new MenuProduct(product.getId(), product.getPrice(), 2L))));
            productService.changePrice(product.getId(), changePriceRequest(8_000L));
            assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
        }

        private ProductPriceRequest changePriceRequest(final long price) {
            return changePriceRequest(BigDecimal.valueOf(price));
        }

        private ProductPriceRequest changePriceRequest(final BigDecimal price) {
            return new ProductPriceRequest(price);
        }
    }
}
