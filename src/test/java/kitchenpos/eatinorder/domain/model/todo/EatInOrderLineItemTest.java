package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderLineItemTest {
    @DisplayName("EatInOrderLineItem을 생성한다.")
    @Test
    void create() {
        // given
        final long seq = 1L;
        final UUID menuId = UUID.randomUUID();
        final long quantity = 1L;
        final long menuPrice = 1L;

        // when
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.of(seq, menuId, quantity, menuPrice, true);

        // then
        assertAll(
                () -> assertThat(eatInOrderLineItem).isNotNull(),
                () -> assertThat(eatInOrderLineItem.getSeq()).isEqualTo(seq),
                () -> assertThat(eatInOrderLineItem.getMenuId()).isEqualTo(menuId),
                () -> assertThat(eatInOrderLineItem.getQuantity()).isEqualTo(quantity),
                () -> assertThat(eatInOrderLineItem.getPrice()).isEqualTo(menuPrice)
        );
    }

    @DisplayName("Order Line Item`에 포함된 `Menu`는 반드시 `Display Menu`여야 한다.")
    @Test
    void createWithNotDisplayMenu() {
        // given
        UUID menuId = UUID.randomUUID();
        boolean isDisplayedMenu = false;

        // when
        final Throwable thrown = catchThrowable(() -> EatInOrderLineItem.of(1L, menuId, 1L, 1L, isDisplayedMenu));

        // then
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessage("주문할 수 없는 메뉴입니다. menuId=" + menuId);
    }
}