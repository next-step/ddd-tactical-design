package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("메뉴 가격")
class MenuPriceTest {


    @DisplayName("가격 생성")
    @Nested
    class create {

        @DisplayName("[성공] 메뉴 가격을 생성한다")
        @Test
        void create1() {
            assertThatNoException().isThrownBy(
                    () -> new MenuPrice(BigDecimal.TEN)
            );
        }

        @DisplayName("[실패] 메뉴 가격은 0 이상이다")
        @ParameterizedTest
        @ValueSource(strings = {"-10", "-200"})
        void create2(BigDecimal price) {
            assertThatThrownBy(
                    () -> new MenuPrice(price))
                    .isInstanceOf(MenuPriceException.class)
                    .hasMessage(MenuErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO.getMessage());
        }

        @DisplayName("[실패] 메뉴 가격은 null일 수 없다")
        @ParameterizedTest
        @NullSource
        void create3(BigDecimal price) {
            assertThatThrownBy(
                    () -> new MenuPrice(price))
                    .isInstanceOf(MenuPriceException.class)
                    .hasMessage(MenuErrorCode.PRICE_IS_NULL.getMessage());
        }
    }

    @DisplayName("더하기")
    @Test
    void add() {
        MenuPrice ten = new MenuPrice(10);
        MenuPrice hundred = new MenuPrice(100);
        MenuPrice add = ten.add(hundred);
        assertThat(add).isEqualTo(new MenuPrice(110));
    }

    @DisplayName("곱하기")
    @Test
    void multiply() {
        MenuPrice ten = new MenuPrice(10);
        MenuPrice multiply = ten.multiply(10);
        assertThat(multiply).isEqualTo(new MenuPrice(100));
    }

    @DisplayName("보다 크다")
    @Test
    void isGreaterThan() {
        MenuPrice ten = new MenuPrice(10);
        assertThat(ten.isGreaterThan(BigDecimal.ONE)).isTrue();
    }

}
