package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.products.exception.ProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 상품 가격이 올바르지 않으면 생성되지 않는다.
 */
class ProductPriceTest {

    @DisplayName("상품 가격은 0 보다 작으면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-10, -1})
    void product_price_under_zero(int value) {
        assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(value)))
                .isInstanceOf(ProductPriceException.class);
    }

    @Nested
    @DisplayName("상품 가격 변경 테스트")
    class ChangeProductPriceCommand {

        @DisplayName("상품 가격이 0 보다 작으면 상품 가격을 변경할 수 없다.")
        @ParameterizedTest
        @ValueSource(ints = {-10, -1})
        void change_price_under_zero(int changePrice) {
            ProductPrice price = new ProductPrice(BigDecimal.TEN);
            assertThatThrownBy(() -> price.changePrice(BigDecimal.valueOf(changePrice)))
                    .isInstanceOf(ProductPriceException.class);
        }

        @DisplayName("상품 가격을 변경할 수 있다.")
        @ParameterizedTest
        @ValueSource(ints = {0, 10 , 100})
        void change_price(int changePrice) {
            ProductPrice price = new ProductPrice(BigDecimal.TEN);
            ProductPrice afterPrice = price.changePrice(BigDecimal.valueOf(changePrice));

            assertThat(afterPrice).isEqualTo(new ProductPrice(BigDecimal.valueOf(changePrice)));
        }
    }
}