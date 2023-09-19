package kitchenpos.menus.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeMenuProductQuantityTest {

    @DisplayName("메뉴 상품 수량을 생성한다.")
    @Test
    void create01() {
        TobeMenuProductQuantity quantity = new TobeMenuProductQuantity(3L);

        assertThat(quantity).isEqualTo(new TobeMenuProductQuantity(3L));
    }

    @DisplayName("메뉴 상품 수량은 0보다 작을 수 없다.")
    @Test
    void create02() {
        assertThatThrownBy(() -> new TobeMenuProductQuantity(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 상품 수량은 0보다 작을 수 없습니다.");
    }
}
