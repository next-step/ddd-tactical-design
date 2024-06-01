package kitchenpos.menu.tobe.domain;

import kitchenpos.menu.tobe.domain.menu.MenuPrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class MenuPriceTest {

    @Test
    @DisplayName("메뉴 가격이 null이라면 예외가 발생한다.")
    void test1() {
        Assertions.assertThatThrownBy(() -> new MenuPrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격이 음수라면 예외가 발생한다.")
    void test2() {
        Assertions.assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(-1000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격을 정상적으로 생성할 수 있다.")
    void test3() {
        BigDecimal validPrice = BigDecimal.valueOf(1000L);
        MenuPrice menuPrice = new MenuPrice(validPrice);

        Assertions.assertThat(menuPrice).isNotNull();
        Assertions.assertThat(menuPrice.getPrice()).isEqualTo(validPrice);
    }

}