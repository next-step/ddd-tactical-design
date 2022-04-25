package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import kitchenpos.menus.tobe.domain.MenuPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
    메뉴 가격은 0보다 작을 수 없다.
 */
class MenuPriceTest {

    @DisplayName("메뉴 가격이 0이상 이라면 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 100})
    void menu_price_over_zero(final long price) {
        assertAll(
                () -> assertDoesNotThrow(() -> new MenuPrice(BigDecimal.valueOf(price))),
                () -> assertThat(new MenuPrice(BigDecimal.valueOf(price))).isInstanceOf(MenuPrice.class)
        );
    }

    @DisplayName("메뉴 가격이 0보다 작다면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(longs = {-100, -1})
    void menu_price_under_zero(final long price) {
        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
