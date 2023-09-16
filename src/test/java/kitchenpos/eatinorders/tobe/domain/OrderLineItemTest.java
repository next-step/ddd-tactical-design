package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemTest {

    @DisplayName("주문내역을 생성할수 있다")
    @Test
    void test1() {
        //given
        OrderLineItemQuantity quantity = new OrderLineItemQuantity(1);

        //when
        OrderLineItem orderLineItem = new OrderLineItem(UUID.randomUUID(), quantity);

        //then
        assertAll(
            () -> assertThat(orderLineItem.getSeq()).isNull(),
            () -> assertThat(orderLineItem.getMenuId()).isNotNull(),
            () -> assertThat(orderLineItem.getQuantity()).isEqualTo(new OrderLineItemQuantity(1))
        );
    }

    @DisplayName("주문내역에 메뉴가 있어야 한다")
    @Test
    void test2() {
        //given
        UUID nullMenuId = null;

        //when && then
        assertThatThrownBy(
            () -> new OrderLineItem(nullMenuId, new OrderLineItemQuantity(1))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문내역엔 메뉴가 있어야 합니다");
    }

    @DisplayName("주문내역에 수량이 있어야 한다")
    @Test
    void test3() {
        //given
        OrderLineItemQuantity emptyQuantity = null;

        //when && then
        assertThatThrownBy(
            () -> new OrderLineItem(UUID.randomUUID(), emptyQuantity)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문내역엔 수량이 있어야 합니다");
    }
}