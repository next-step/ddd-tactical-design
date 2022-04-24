package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.menu.domain.MenuPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @Test
    void 가격이_음수면_예외가_발생한다() {
        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(-16_000)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 동등성() {
        final MenuPrice price1 = new MenuPrice(BigDecimal.valueOf(16_000));
        final MenuPrice price2 = new MenuPrice(BigDecimal.valueOf(16_000));
        assertThat(price1).isEqualTo(price2);
    }
}
