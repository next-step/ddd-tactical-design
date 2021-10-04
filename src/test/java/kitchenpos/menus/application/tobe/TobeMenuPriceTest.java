package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TobeMenuPriceTest {

    @DisplayName("메뉴 가격 생성")
    @Test
    void createProductPrice() {
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(1000L));

        assertThat(menuPrice).isNotNull();
    }

    @DisplayName("잘못된 금액")
    @ValueSource(longs = {-1L, -100L})
    @ParameterizedTest
    void negativePrice(final Long price) {
        assertThatThrownBy(
                () -> new MenuPrice(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("동등성")
    @Test
    void equalPrice() {
        MenuPrice menuPrice1 = new MenuPrice(BigDecimal.valueOf(1000L));
        MenuPrice menuPrice2 = new MenuPrice(BigDecimal.valueOf(1000L));

        assertThat(menuPrice1).isEqualTo(menuPrice2);
    }
}
