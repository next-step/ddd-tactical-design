package kitchenpos.menus.tobe.domain.vo;

import static kitchenpos.menus.tobe.domain.MenuFixtures.menuPrice;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import kitchenpos.menus.tobe.domain.exception.MinimumMenuPriceException;
import kitchenpos.menus.tobe.domain.exception.NullMenuPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

    @DisplayName("가격을 등록할 수 있다.")
    @Test
    void create() {
        MenuPrice actual = menuPrice(BigDecimal.ZERO);

        assertThat(actual).isEqualTo(menuPrice(BigDecimal.ZERO));
        assertThat(actual.hashCode() == menuPrice(BigDecimal.ZERO).hashCode())
                .isTrue();
    }

    @DisplayName("지정된 가격보다 큰지 비교한다.")
    @Test
    void grateThan() {
        MenuPrice actual = menuPrice(BigDecimal.ONE);

        assertThat(actual.grateThan(BigDecimal.ZERO)).isTrue();
        assertThat(actual.grateThan(BigDecimal.TEN)).isFalse();
    }

    @DisplayName("메뉴 가격 에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("반드시 값이 존재해야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullSource
        void error1(BigDecimal price) {
            assertThatExceptionOfType(NullMenuPriceException.class)
                    .isThrownBy(() -> menuPrice(price));
        }

        @DisplayName("반드시 0원 이상이어야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1")
        void error2(BigDecimal price) {
            assertThatExceptionOfType(MinimumMenuPriceException.class)
                    .isThrownBy(() -> menuPrice(price));
        }
    }
}
