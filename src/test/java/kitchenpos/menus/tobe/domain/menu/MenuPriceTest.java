package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.exception.InvalidMenuPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 가격")
class MenuPriceTest {
    @DisplayName("[성공] 메뉴의 가격을 생성한다.")
    @Test
    void create() {
        MenuPrice price = MenuPrice.from(BigDecimal.valueOf(10_000));

        assertThat(price).isEqualTo(MenuPrice.from(10_000));
    }

    @DisplayName("[실패] 메뉴의 가격은 0원 이상이어야 한다.")
    @ValueSource(strings = {"-1", "-1000", "-9999999"})
    @NullSource
    @ParameterizedTest
    void fail1(BigDecimal price) {
        assertThatThrownBy(() -> MenuPrice.from(price))
                .isInstanceOf(InvalidMenuPriceException.class);
    }
}
