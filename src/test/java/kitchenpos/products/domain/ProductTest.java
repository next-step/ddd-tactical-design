package kitchenpos.products.domain;

import kitchenpos.Fixtures;
import kitchenpos.products.domain.exception.InvalidProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    @DisplayName("[오류] 음수는 입력할 수 없습니다.")
    @ValueSource(strings = {"-1", "-200", "-3000"})
    @NullSource
    @ParameterizedTest
    void product_price_not_negative_number_test(BigDecimal negativeNumber) {
        assertThatThrownBy(() -> Fixtures.product(negativeNumber))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @DisplayName("[정상] 상품 가격은 0원 이상이어야 한다.")
    @ValueSource(strings = {"0", "1", "200", "3000"})
    @ParameterizedTest
    void product_price_not_positive_number_test(BigDecimal positiveNumber) {
        Product product = Fixtures.product(positiveNumber);
        assertAll(() -> product.changePrice(positiveNumber),
                () -> assertThat(product.getPrice()).isEqualTo(positiveNumber));
    }
}