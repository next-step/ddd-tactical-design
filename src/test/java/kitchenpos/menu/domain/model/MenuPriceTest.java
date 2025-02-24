package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuPriceValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuPriceTest {
    @DisplayName("MenuPrice를 생성할 수 있다")
    @Test
    void createMenuPrice() {
        // given
        final BigDecimal price = BigDecimal.valueOf(10000);

        // when
        final MenuPrice menuPrice = MenuPrice.of(price, price);

        // then
        assertThat(menuPrice.value()).isEqualTo(price);
    }

    @DisplayName("MenuPrice에 빈 값이 들어가면 예외가 발생한다")
    @Test
    void createMenuPriceWithEmptyOrNull() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuPrice.of(null, BigDecimal.ZERO));

        // then
        assertThat(thrown).isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("메뉴 가격은 0보다 큰 금액이어야 합니다.");
    }

    @DisplayName("MenuPrice에 음수 값이 들어가면 예외가 발생한다")
    @Test
    void createMenuPriceWithNegative() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuPrice.of(BigDecimal.valueOf(-1), BigDecimal.ZERO));

        // then
        assertThat(thrown).isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("메뉴 가격은 0보다 큰 금액이어야 합니다.");
    }

    @DisplayName("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 한다")
    @Test
    void createMenuPriceWithInvalidValidator() {
        // given
        final BigDecimal menuPrice = BigDecimal.valueOf(10_000);
        final BigDecimal menuProductTotalPrice = BigDecimal.valueOf(5_000);

        // when
        final Throwable thrown = catchThrowable(() -> MenuPrice.of(menuPrice, menuProductTotalPrice));

        // then
        assertThat(thrown).isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
    }
}