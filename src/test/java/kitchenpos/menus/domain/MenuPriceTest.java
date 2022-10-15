package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuPriceTest {

    @Test
    @DisplayName("메뉴가격을 생성한다.")
    void createMenuPrice() {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(15000);

        // when
        MenuPrice price = new MenuPrice(priceValue);

        // then
        assertThat(price).isEqualTo(new MenuPrice(priceValue));
    }

    @ParameterizedTest
    @DisplayName("상품 가격이 0보다 작을 경우 Exception을 발생시킨다.")
    @ValueSource(ints = {-1000, -50000})
    void createMenuPrice(int value) {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(value);

        // when
        // then
        assertThatThrownBy(() -> new MenuPrice(priceValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    void compareMenuPriceAndProductsPrice() {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(15000);

        // when
        MenuPrice price = new MenuPrice(priceValue);

        // then
        assertThatThrownBy(() -> price.compareProductsPrice(BigDecimal.valueOf(14000)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
