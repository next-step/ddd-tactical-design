package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuProductQuantityTest {

    @Test
    @DisplayName("메뉴 상품 수를 생성한다.")
    void createMenuProductQuantity() {
        // given
        long quantity = 10;

        // when
        MenuProductQuantity menuProductQuantity = new MenuProductQuantity(quantity);

        // then
        assertThat(menuProductQuantity).isEqualTo(new MenuProductQuantity(quantity));
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품의 수가 0보다 작을 경우 Exception을 발생시킨다.")
    @ValueSource(ints = {-10, -50})
    void createMenuProductQuantity(int quantity) {
        // when
        // then
        assertThatThrownBy(() -> new MenuProductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
