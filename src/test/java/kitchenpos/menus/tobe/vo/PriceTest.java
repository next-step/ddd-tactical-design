package kitchenpos.menus.tobe.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("가격")
class PriceTest {

    @DisplayName("가격 생성")
    @Test
    void createPrice() {
        Price price = new Price(20_000);

        assertThat(price.getValue()).isEqualTo(BigDecimal.valueOf(20_000));
    }

    @DisplayName("상품 가격이 0보다 작으면 에러")
    @ValueSource(longs = {-1L, -10_000L})
    @ParameterizedTest
    void createPriceNegative(long value) {
        assertThatThrownBy(() -> new Price(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
