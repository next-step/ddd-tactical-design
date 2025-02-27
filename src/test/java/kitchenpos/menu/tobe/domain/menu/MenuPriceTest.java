package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.exception.MenuException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @Test
    @DisplayName("MenuPrice 객체를 생성할 수 있다")
    void create() {
        // when
        MenuPrice price = MenuPrice.from(10000L);

        // then
        assertThat(price.getValue()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("가격이 null이면 예외가 발생한다")
    void createWithNullPrice() {
        // when & then
        assertThatThrownBy(() -> MenuPrice.from(null))
                .isInstanceOf(MenuException.class);
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -1000L})
    @DisplayName("가격이 0 이하이면 예외가 발생한다")
    void createWithNonPositivePrice(Long price) {
        // when & then
        assertThatThrownBy(() -> MenuPrice.from(price))
                .isInstanceOf(MenuException.class);
    }

    @Test
    @DisplayName("MenuPrice 객체끼리 동등성 비교가 가능하다")
    void equals() {
        // given
        MenuPrice price1 = MenuPrice.from(10000L);
        MenuPrice price2 = MenuPrice.from(10000L);
        MenuPrice price3 = MenuPrice.from(20000L);

        // then
        assertThat(price1).isEqualTo(price2);
        assertThat(price1).isNotEqualTo(price3);
    }
}