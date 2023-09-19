package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {
    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> new MenuPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 같으면 같은 메뉴 가격이다.")
    @Test
    void equals() {
        final MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(1000));
        final MenuPrice sameMenuPrice = new MenuPrice(BigDecimal.valueOf(1000));

        assertThat(menuPrice).isEqualTo(sameMenuPrice);
    }
}
