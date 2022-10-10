package kitchenpos.eatinorders.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {

    private EatInOrderId eatInOrderId;
    private OrderLineItems orderLineItems;
    private OrderTable orderTable;

    @BeforeEach
    void setup() {
        eatInOrderId = new EatInOrderId(UUID.randomUUID());
        orderLineItems = new OrderLineItems(List.of(new OrderLineItem(1L, BigDecimal.TEN, UUID.randomUUID(), true, BigDecimal.TEN, 1L)));
        orderTable = new OrderTable(new OrderTableId(UUID.randomUUID()), new OrderTableName("1번 테이블"));
        orderTable.occupy();
    }

    @Test
    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다")
    void constructor() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);
        assertThat(expected).isNotNull();
    }

    @Test
    @DisplayName("메뉴가 없으면 등록할 수 없다")
    void constructor_with_empty_menu() {
        assertThatThrownBy(() -> new EatInOrder(eatInOrderId, new OrderLineItems(List.of(new OrderLineItem(1L, BigDecimal.TEN, null, true, BigDecimal.TEN, 1L))), orderTable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    void constructor_with_negative_quantity() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, new OrderLineItems(List.of(new OrderLineItem(1L, BigDecimal.TEN, UUID.randomUUID(), true, BigDecimal.TEN, -1L))), orderTable);
        assertThat(expected).isNotNull();
    }

    @Test
    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    void constructor_with_not_occupied_order_table() {
        orderTable.clear();
        assertThatThrownBy(() -> new EatInOrder(eatInOrderId, orderLineItems, orderTable))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    void constructor_with_no_displayed_menu() {
        assertThatThrownBy(() -> new EatInOrder(eatInOrderId, new OrderLineItems(List.of(new OrderLineItem(1L, BigDecimal.TEN, UUID.randomUUID(), false, BigDecimal.TEN, 1L))), orderTable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    void constructor_with_not_matched_price() {
        assertThatThrownBy(() -> new EatInOrder(eatInOrderId, new OrderLineItems(List.of(new OrderLineItem(1L, BigDecimal.TEN, UUID.randomUUID(), true, BigDecimal.ONE, 1L))), orderTable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문을 접수한다.")
    void constructor_with() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);
        expected.accept();

        assertThat(expected.status()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    void aceept() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);
        expected.accept();

        assertThatThrownBy(() -> expected.accept())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 서빙한다.")
    void serve() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);
        expected.accept();
        expected.serve();

        assertThat(expected.status()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    @DisplayName("접수된 주문만 서빙할 수 있다.")
    void serve_with_not_accept() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);

        assertThatThrownBy(() -> expected.serve())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문을 완료한다.")
    void complete() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);

        expected.accept();
        expected.serve();
        expected.complete();

        assertThat(expected.status()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    void clear() {
        final EatInOrder expected = new EatInOrder(eatInOrderId, orderLineItems, orderTable);

        expected.accept();
        expected.serve();
        expected.complete();

        final OrderTable clearOrderTable = expected.orderTable();
        assertThat(clearOrderTable.isOccupied()).isFalse();
        assertThat(clearOrderTable.guestCount()).isEqualTo(GuestCount.zeroGuestCount());
    }
}
