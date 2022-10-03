package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.menus.exception.InvalidMenuProductQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductQuantityTest {

    @DisplayName("메뉴의 상품 수량은 0보다 작을 수 없다.")
    @ValueSource(strings = {"-1", "-10"})
    @ParameterizedTest
    void negativeException(long negativePrice) {
        assertThatThrownBy(() -> new MenuProductQuantity(negativePrice))
            .isExactlyInstanceOf(InvalidMenuProductQuantityException.class);
    }
}
