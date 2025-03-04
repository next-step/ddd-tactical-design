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
    
    @DisplayName("금액을 곱할 수 있다")
    @Test
    void multiply(){
        Price price = new Price(1000);

        Price result1 = price.multiply(2);
        Price result2 = price.multiply(new PositiveNumber(3));

        assertThat(result1).isEqualTo(new Price(2000));
        assertThat(result2).isEqualTo(new Price(3000));
    }

    @DisplayName("어느 가격이 더 큰지 비교할 수 있다")
    @Test
    void isGreaterThan(){
        Price price = new Price(2000);

        boolean result = price.isGreaterThan(new Price(1000));

        assertThat(result).isTrue();
    }

    @DisplayName("동일한 가격인지 확인한다")
    @Test
    void same(){
        Price price1 = new Price(2000);
        Price price2 = new Price(2000);

        boolean result = price1.same(price2);

        assertThat(result).isTrue();
    }
    
    @DisplayName("가격이 다른지 확인한다")
    @Test
    void result(){
        Price price1 = new Price(1000);
        Price price2 = new Price(2000);

        boolean result = price1.different(price2);

        assertThat(result).isTrue();
    }
}
