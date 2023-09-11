package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.TobeFixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;

class TobeMenuTest {

    @Test
    void changePrice() {
        Menu menu = menu();
        assertThat(menu.isDisplayed()).isTrue();

        // when
        menu.changePrice(BigDecimal.valueOf(Double.MAX_VALUE));

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

}