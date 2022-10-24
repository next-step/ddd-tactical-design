package kitchenpos.menu.tobe.domain.vo;

import static kitchenpos.menu.Fixtures.menuProduct;
import static kitchenpos.product.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.common.price.Price;
import kitchenpos.product.tobe.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductTest {

    @DisplayName("subtotal()")
    @Nested
    class Cvxueewc {

        @DisplayName("소계가 올바르게 계산되어야 한다.")
        @ValueSource(longs = {
            16, 8, 32, 4, 1,
            15, 13, 3, 31, 18,
        })
        @ParameterizedTest
        void czxovwxu(final long quantity) {
            final long pricePerUnit = 10000;
            final Product product = product("상품", pricePerUnit);
            final MenuProduct menuProduct = menuProduct(product, quantity);

            assertThat(menuProduct.subtotal()).isEqualTo(new Price(pricePerUnit * quantity));
        }
    }
}
