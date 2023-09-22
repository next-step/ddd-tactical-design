package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.MenuPricePolicy;
import kitchenpos.menus.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuPricePolicyTest {
    private final MenuPricePolicy menuPricePolicy = new MenuPricePolicy();
    @ParameterizedTest
    @ValueSource(ints = {10000, 10001})
    void Menu의_Price는_MenuProducts의_Price_총합보다_작거나_같아야_한다(int menuProductsTotalPrice) {
        assertDoesNotThrow(() -> menuPricePolicy.follow(new Price(10000), new Price(menuProductsTotalPrice)));
    }

    @Test
    void Menu의_Price는_MenuProducts의_Price_총합보다_클_수_없다() {
        assertThatThrownBy(() -> menuPricePolicy.follow(new Price(10000), new Price(9999)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴의 가격은 메뉴에 속한 상품들의 가격 총합보다 작거나 같아야 합니다. 현재 값: 메뉴가격 10000, 메뉴에 속한 상품들 가격 총합 9999");
    }
}
