package kitchenpos.menus.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    @Test
    void hideIfMenuPriceTooHigher() {
        Menu menu = menu();
        assertThat(menu.isDisplayed()).isTrue();

        // when
        menu.setPrice(BigDecimal.valueOf(Double.MAX_VALUE));
        menu.hideIfMenuPriceTooHigher();

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}