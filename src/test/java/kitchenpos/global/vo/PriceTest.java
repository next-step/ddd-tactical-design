package kitchenpos.global.vo;

import static kitchenpos.global.TobeFixtures.price;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import kitchenpos.global.exception.MaximumPriceException;
import kitchenpos.global.exception.MinimumPriceException;
import kitchenpos.global.exception.NullPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격을 생성할할 수 있다.")
    @Test
    void create() {
        Price actual = price(BigDecimal.ZERO);

        assertThat(actual).isEqualTo(price(BigDecimal.ZERO));
        assertThat(actual.hashCode() == price(BigDecimal.ZERO).hashCode()).isTrue();
    }

    @DisplayName("가격을 곱한다.")
    @Test
    void multiply() {
        Price price = price(BigDecimal.TEN);
        Quantity quantity = new Quantity(1);

        assertThat(price.multiply(quantity))
                .isEqualTo(price(BigDecimal.TEN));
    }

    @DisplayName("지정된 가격보다 큰지 비교한다.")
    @Test
    void grateThan() {
        Price actual = price(BigDecimal.ONE);

        assertThat(actual.grateThan(price(BigDecimal.ZERO))).isTrue();
        assertThat(actual.grateThan(price(BigDecimal.TEN))).isFalse();
    }

    @DisplayName("가격 에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("반드시 값이 존재해야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullSource
        void error1(BigDecimal price) {
            assertThatExceptionOfType(NullPriceException.class)
                    .isThrownBy(() -> price(price));
        }

        @DisplayName("반드시 0원 이상이어야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1")
        void error2(BigDecimal actual) {
            assertThatExceptionOfType(MinimumPriceException.class)
                    .isThrownBy(() -> price(actual));
        }

        @DisplayName("특정 가격을 초과한 경우 예외가 발생한다.")
        @Test
        void error3() {
            Price limit = price(BigDecimal.ZERO);
            Price price = price(BigDecimal.ONE);

            assertThatExceptionOfType(MaximumPriceException.class)
                    .isThrownBy(() -> price.validateLessThan(limit));
        }
    }
}
