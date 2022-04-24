package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MenuPriceTest {

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1000", "-2000"})
    @NullSource
    void positive_price(BigDecimal value) {
        assertThatCode(() -> new MenuPrice(value)).isNotNull();
    }

    @DisplayName("가격을 곱한 값을 리턴한다.")
    @Test
    void multiply() {
        final MenuPrice menuPrice = MenuPrice.valueOf(1000);
        final BigDecimal other = BigDecimal.TEN;

        MenuPrice actual = menuPrice.multiply(other);

        assertThat(actual).extracting("price").isEqualTo(BigDecimal.valueOf(10000));
    }

    @DisplayName("가격을 합한 값을 리턴한다.")
    @Test
    void add() {
        final MenuPrice menuPrice = MenuPrice.valueOf(1000);
        final BigDecimal other = BigDecimal.valueOf(1000);

        MenuPrice actual = menuPrice.add(other);

        assertThat(actual).extracting("price").isEqualTo(BigDecimal.valueOf(2000));
    }
}
