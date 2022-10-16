package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuPriceTest {

    private final BigDecimal menuProductPriceSum = BigDecimal.valueOf(15000);

    @Test
    @DisplayName("메뉴가격을 생성한다.")
    void createMenuPrice() {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(15000);

        // when
        MenuPrice price = new MenuPrice(priceValue, menuProductPriceSum);

        // then
        assertThat(price).isEqualTo(new MenuPrice(priceValue, menuProductPriceSum));
    }

    @ParameterizedTest
    @DisplayName("상품 가격이 0보다 작을 경우 Exception을 발생시킨다.")
    @ValueSource(ints = {-1000, -50000})
    void createMenuPrice(int value) {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(value);

        // when
        // then
        assertThatThrownBy(() -> new MenuPrice(priceValue, menuProductPriceSum))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    void compareMenuPriceAndProductsPrice() {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(15000);

        // when
        // then
        assertThatThrownBy(() -> new MenuPrice(priceValue, BigDecimal.valueOf(14000)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
