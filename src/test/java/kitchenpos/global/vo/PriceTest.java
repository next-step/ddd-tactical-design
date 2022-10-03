package kitchenpos.global.vo;

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
        Price actual = price(0);

        assertThat(actual).isEqualTo(price(0));
        assertThat(actual.hashCode() == price(0).hashCode()).isTrue();
    }

    @DisplayName("가격을 곱한다.")
    @Test
    void multiply() {
        Price price = price(10);
        Quantity quantity = new Quantity(1);

        assertThat(price.multiply(quantity))
                .isEqualTo(price(10));
    }

    @DisplayName("가격을 합한다.")
    @Test
    void add() {
        Price price = price(0);

        assertThat(price.add(price(10)))
                .isEqualTo(price(10));
    }

    @DisplayName("지정된 가격보다 큰지 비교한다.")
    @Test
    void grateThan() {
        Price actual = price(1);

        assertThat(actual.grateThan(price(0))).isTrue();
        assertThat(actual.grateThan(price(1))).isFalse();
        assertThat(actual.grateThan(price(2))).isFalse();
    }

    @DisplayName("지정된 가격보다 작은지 비교한다.")
    @Test
    void lessThan() {
        Price price = price(1);

        assertThat(price.lessThan(price(0))).isFalse();
        assertThat(price.lessThan(price(1))).isFalse();
        assertThat(price.lessThan(price(2))).isTrue();
    }
    
    @DisplayName("지정된 가격보다 같은지 비교한다.")
    @Test
    void isSame() {
        Price price = price(1);

        assertThat(price.isSame(price(0))).isFalse();
        assertThat(price.isSame(price(1))).isTrue();
        assertThat(price.isSame(price(2))).isFalse();
    }

    @DisplayName("가격 에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("반드시 값이 존재해야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullSource
        void error1(BigDecimal price) {
            assertThatExceptionOfType(NullPriceException.class)
                    .isThrownBy(() -> new Price(price));
        }

        @DisplayName("반드시 0원 이상이어야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1")
        void error2(BigDecimal actual) {
            assertThatExceptionOfType(MinimumPriceException.class)
                    .isThrownBy(() -> new Price(actual));
        }

        @DisplayName("특정 가격을 초과한 경우 예외가 발생한다.")
        @Test
        void error3() {
            Price limit = price(0);
            Price price = price(1);

            assertThatExceptionOfType(MaximumPriceException.class)
                    .isThrownBy(() -> price.validateLessThan(limit));
        }
    }

    private Price price(long value) {
        return new Price(BigDecimal.valueOf(value));
    }
}
