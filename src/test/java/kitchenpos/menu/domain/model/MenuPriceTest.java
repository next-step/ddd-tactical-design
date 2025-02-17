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
        final MenuPrice menuPrice = MenuPrice.of(price, p -> {});

        // then
        assertThat(menuPrice.value()).isEqualTo(price);
    }

    @DisplayName("MenuPrice에 빈 값이 들어가면 예외가 발생한다")
    @Test
    void createMenuPriceWithEmptyOrNull() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuPrice.of(null, p -> {}));

        // then
        assertThat(thrown).isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("메뉴 가격은 0보다 큰 금액이어야 합니다.");
    }

    @DisplayName("MenuPrice에 음수 값이 들어가면 예외가 발생한다")
    @Test
    void createMenuPriceWithNegative() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuPrice.of(BigDecimal.valueOf(-1), p -> {}));

        // then
        assertThat(thrown).isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("메뉴 가격은 0보다 큰 금액이어야 합니다.");
    }

    @DisplayName("MenuPrice의 검증이 실패하면 예외가 발생한다")
    @Test
    void createMenuPriceWithInvalidValidator() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuPrice.of(BigDecimal.valueOf(10000), p -> {
            throw new MenuPriceValidationException("검증 실패");
        }));

        // then
        assertThat(thrown).isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("검증 실패");
    }
}