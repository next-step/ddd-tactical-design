package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("상품가격테스트")
class PriceTest {

    @DisplayName("가격생성성공")
    @Test
    void create() {
        Price price = Price.of(BigDecimal.valueOf(1));
        assertThat(price.getPrice()).isEqualTo(1);
    }

    @DisplayName("가격은 0 보다 작으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-2, -1})
    void price_more_than_zero(int input) {
        assertThatThrownBy(() -> Price.of(BigDecimal.valueOf(input)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_PRICE_MORE_ZERO);
    }

}
