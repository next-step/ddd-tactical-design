package kitchenpos.eatinorders.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 같은 menu id 를 갖는 orderLineItem 이 중복될 수 없다.
 */
class OrderLineItemsTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        OrderLineItem orderLineItem1 = new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN);
        OrderLineItem orderLineItem2 = new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN);

        assertDoesNotThrow(() -> new OrderLineItems(Arrays.asList(orderLineItem1, orderLineItem2)));
    }

    @DisplayName("중복된 메뉴 id 를 갖는 orderLineItem 이 있는 경우 예외.")
    @Test
    void duplicate_menu_id() {
        OrderLineItem orderLineItem = new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN);

        assertThatThrownBy(() -> new OrderLineItems(Arrays.asList(orderLineItem, orderLineItem, orderLineItem)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("OrderLineItem 의 총 금액 계산.")
    @Test
    void calc_total_price() {
        OrderLineItems sut = new OrderLineItems(
                Arrays.asList(
                        new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN),
                        new OrderLineItem(5L, UUID.randomUUID(), BigDecimal.ONE),
                        new OrderLineItem(10L, UUID.randomUUID(), BigDecimal.TEN)
                )
        );

        assertThat(sut.totalPrice()).isEqualTo(BigDecimal.valueOf(135));
    }
}
