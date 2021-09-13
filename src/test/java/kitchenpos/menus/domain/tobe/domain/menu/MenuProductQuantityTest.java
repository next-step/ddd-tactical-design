package kitchenpos.menus.domain.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductQuantityTest {

    @DisplayName("메뉴 상품의 수량을 생성할 수 있다.")
    @ValueSource(strings = {"0", "1"})
    @ParameterizedTest
    void 생성(final Long quantity) {
        assertDoesNotThrow(
            () -> new MenuProductQuantity(quantity)
        );
    }

    @DisplayName("메뉴 상품의 수량은 null이 될 수 없다.")
    @NullSource
    @ParameterizedTest
    void null수량(final Long quantity) {
        assertThatThrownBy(
            () -> new MenuProductQuantity(quantity)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품의 수량은 음수(-)일 수 없다.")
    @ValueSource(strings = {"-1"})
    @ParameterizedTest
    void 마이너스수량(final Long quantity) {
        assertThatThrownBy(
            () -> new MenuProductQuantity(quantity)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹 이름 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final MenuProductQuantity quantity1 = new MenuProductQuantity(1L);
        final MenuProductQuantity quantity2 = new MenuProductQuantity(1L);

        assertThat(quantity1).isEqualTo(quantity2);
    }
}
