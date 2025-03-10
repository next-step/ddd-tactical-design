package kitchenpos.menus.tobe.domain.menuproduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.mock.adapter.FakeProductPriceAdapter;
import kitchenpos.mock.fixture.ProductFixture;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuProductsTest {

    FakeProductPriceAdapter productPriceAdapter = new FakeProductPriceAdapter(null);

    @DisplayName("메뉴 상품 리스트를 생성할 수 있다.")
    @Test
    void create() {
        // given
        final long seq = 1L;
        final BigDecimal price = FakeProductPriceAdapter.PRICE;
        final long quantity = 1L;
        final Product product = ProductFixture.create(price);

        List<MenuProduct> menuProductList = List.of(
            new MenuProduct(seq, product.getId(), quantity),
            new MenuProduct((seq + 1), product.getId(), quantity)
        );

        // when
        MenuProducts menuProducts = new MenuProducts(menuProductList);

        // then
        assertThat(menuProducts).isNotNull();
        assertThat(menuProducts.getValue()).hasSize(2);
        assertThat(menuProducts.getValue()).containsExactlyElementsOf(menuProductList);

        menuProducts.getValue().forEach(menuProduct -> {
            assertThat(menuProduct.getProductId()).isEqualTo(product.getId());
            assertThat(menuProduct.getQuantity()).isEqualTo(quantity);
            assertThat(menuProduct.calculatePrice(productPriceAdapter)).isEqualTo(
                price.multiply(BigDecimal.valueOf(quantity)));
        });
    }

    @DisplayName("최소 개수 미만의 메뉴 상품 리스트를 생성 시, 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithNull(List<MenuProduct> value) {
        // when then
        assertThatThrownBy(() -> new MenuProducts(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MenuProducts.ERROR_MESSAGE_EMPTY);
    }
}
