package kitchenpos.order.common.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemTest {

    @Test
    @DisplayName("주문 내역의 메뉴 수량은 0 이하면 예외를 던진다.")
    void validate_order_line_item_quantity() {
        // given
        Menu menu = new Menu(UUID.randomUUID(), "김치", BigDecimal.ONE, true);

        // when // then
        assertThatThrownBy(() -> new OrderLineItem(menu, 0, menu.getId(), BigDecimal.ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 내역의 메뉴 수량이 비어있습니다!");
    }

    @Test
    @DisplayName("주문 내역의 메뉴가 게시 상태가 아니면 예외를 던진다.")
    void validate_order_line_item_menu_display() {
        // given
        Menu menu = new Menu(UUID.randomUUID(), "김치", BigDecimal.ONE, false);

        // when // then
        assertThatThrownBy(() -> new OrderLineItem(menu, 1, menu.getId(), BigDecimal.ONE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문 내역의 메뉴가 게시되어 있지 않습니다!");
    }

    @Test
    @DisplayName("주문 내역의 가격과 메뉴의 가격이 다르면 예외를 던진다.")
    void validate_order_line_item_price() {
        // given
        Menu menu = new Menu(UUID.randomUUID(), "김치", BigDecimal.TEN, true);

        // when // then
        assertThatThrownBy(() -> new OrderLineItem(menu, 1, menu.getId(), BigDecimal.ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 내역의 가격이 메뉴의 가격과 다릅니다!");
    }
}
