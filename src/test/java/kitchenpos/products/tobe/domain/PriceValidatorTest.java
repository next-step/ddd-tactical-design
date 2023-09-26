package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceValidatorTest {

    @DisplayName("상품 가격이 없으면 에러를 반환한다.")
    @NullSource
    @ParameterizedTest
    void emptyProductPriceException(BigDecimal price) {
        assertThatThrownBy(() -> new PriceValidator(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상풍 가격은 필수로 존재해야 합니다.");
    }

    @DisplayName("상품가격이 0원 이하이면 에러를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = "-1000")
    void negativePriceException() {
        final BigDecimal price = new BigDecimal(-1000);

        assertThatThrownBy(() -> new PriceValidator(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 가격은 0원 이상이어야 합니다.");
    }

}
