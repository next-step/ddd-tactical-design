package kitchenpos.common;

import kitchenpos.common.domain.Price;
import kitchenpos.common.exception.PriceErrorCode;
import kitchenpos.common.exception.PriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("메뉴 가격")
class PriceTest {


    @DisplayName("가격 생성")
    @Nested
    class create {

        @DisplayName("[성공] 메뉴 가격을 생성한다")
        @Test
        void create1() {
            assertThatNoException().isThrownBy(
                    () -> new Price(BigDecimal.TEN)
            );
        }

        @DisplayName("[실패] 메뉴 가격은 0 이상이다")
        @ParameterizedTest
        @ValueSource(strings = {"-10", "-200"})
        void create2(BigDecimal price) {
            assertThatThrownBy(
                    () -> new Price(price))
                    .isInstanceOf(PriceException.class)
                    .hasMessage(PriceErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO.getMessage());
        }

        @DisplayName("[실패] 메뉴 가격은 null일 수 없다")
        @ParameterizedTest
        @NullSource
        void create3(BigDecimal price) {
            assertThatThrownBy(
                    () -> new Price(price))
                    .isInstanceOf(PriceException.class)
                    .hasMessage(PriceErrorCode.PRICE_IS_NULL.getMessage());
        }
    }

    @DisplayName("더하기")
    @Test
    void add() {
        Price ten = new Price(10);
        Price hundred = new Price(100);
        Price add = ten.add(hundred);
        assertThat(add).isEqualTo(new Price(110));
    }

    @DisplayName("곱하기")
    @Test
    void multiply() {
        Price ten = new Price(10);
        Price multiply = ten.multiply(10);
        assertThat(multiply).isEqualTo(new Price(100));
    }

    @DisplayName("보다 크다")
    @Test
    void isGreaterThan() {
        Price ten = new Price(10);
        assertThat(ten.isGreaterThan(BigDecimal.ONE)).isTrue();
    }

}
