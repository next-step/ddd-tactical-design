package kitchenpos.common.vo;

import kitchenpos.common.exception.NegativePriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {
    @DisplayName("가격이 0원 미만이면 예외가 발생한다")
    @ValueSource(longs = {-1})
    @ParameterizedTest
    void validatePrice(Long price) {
        assertThatThrownBy(() -> new Price(BigDecimal.valueOf(price)))
                .isInstanceOf(NegativePriceException.class);
    }

    @DisplayName("금액을 더할 수 있다")
    @Test
    void add() {
        Price price = new Price(1000);
        long otherPrice1 = 1000;
        Price otherPrice2 = new Price(otherPrice1);
        Price otherPrice3 = new Price(BigDecimal.valueOf(otherPrice1));

        assertThat(price.add(otherPrice1)).isEqualTo(new Price(2000));
        assertThat(price.add(otherPrice2)).isEqualTo(new Price(2000));
        assertThat(price.add(otherPrice3)).isEqualTo(new Price(2000));
    }
}