package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuPriceTest {

    @DisplayName("정상적으로 메뉴의 가격을 생성해보자")
    @ParameterizedTest
    @ValueSource(ints = {0, 1000, 5000, 10000})
    void createMenuPrice(int price) {
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(price));

        assertAll(
                () -> assertThat(menuPrice).isNotNull(),
                () -> assertThat(menuPrice.getPrice()).isEqualTo(BigDecimal.valueOf(price))
        );
    }

    @DisplayName("메뉴가격은 null이 될수 없다.")
    @Test
    void invalidMenuPrice() {
        assertThatThrownBy(
                () -> new MenuPrice(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격은 음수가 될수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -5000, -10000})
    void negativeMenuPrice(int price) {
        assertThatThrownBy(
                () -> new MenuPrice(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
