package kitchenpos.global.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("가격")
class PriceTest {

    @DisplayName("가격 생성")
    @ValueSource(longs = {0, 20_000})
    @ParameterizedTest
    void createPrice(long value) {
        Price price = new Price(value);

        assertThat(price.getValue()).isEqualTo(BigDecimal.valueOf(value));
    }

    @DisplayName("상품 가격이 0보다 작으면 에러")
    @ValueSource(longs = {-1L, -10_000L})
    @ParameterizedTest
    void createPriceNegative(long value) {
        assertThatThrownBy(() -> new Price(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
