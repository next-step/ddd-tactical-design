package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures2.hideMenu;
import static kitchenpos.Fixtures2.menu;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderLineItemTest {

    @DisplayName("1개의 등록된 메뉴로 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new OrderLineItem(menu(), 1));
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @Test
    void negativeQuantity() {
        assertDoesNotThrow(() -> new OrderLineItem(menu(), -1));
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void hidMenu() {
        assertThatThrownBy(() -> new OrderLineItem(hideMenu(), 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("숨겨진 메뉴는 주문할 수 없습니다.");
    }
}
