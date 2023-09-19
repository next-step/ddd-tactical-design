package kitchenpos.menus.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuPriceTest {

    @DisplayName("메뉴 가격을 생성한다.")
    @Test
    void create01() {
        MenuPrice price = new MenuPrice(new BigDecimal(1000));

        assertThat(price).isEqualTo(new MenuPrice(new BigDecimal(1000)));
    }

    @DisplayName("메뉴의 가격에 수량을 곱한다.")
    @Test
    void create02() {
        MenuPrice price = new MenuPrice(new BigDecimal(1000));

        assertThat(price.multiply(3L)).isEqualTo(new MenuPrice(new BigDecimal(3000)));
    }

    @DisplayName("메뉴의 가격에 수량을 더한다.")
    @Test
    void create03() {
        MenuPrice price = new MenuPrice(new BigDecimal(1000));

        MenuPrice actual = price.add(new MenuPrice(new BigDecimal(1000)));

        assertThat(actual).isEqualTo(new MenuPrice(new BigDecimal(2000)));
    }
}
