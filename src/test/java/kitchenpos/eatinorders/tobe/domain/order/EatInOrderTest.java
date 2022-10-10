package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.DisplayedName;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("매장 주문")
class EatInOrderTest {

    private UUID menuId1;
    private UUID menuId2;
    private OrderLineItems orderLineItems;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        menuId1 = UUID.randomUUID();
        menuId2 = UUID.randomUUID();

        orderLineItems = new OrderLineItems(List.of(
                new OrderLineItem(menuId1, new Quantity(2), new Price(15_000)),
                new OrderLineItem(menuId2, new Quantity(1), new Price(20_000))
        ));

        orderTable = new OrderTable(new DisplayedName("1번"), new NumberOfGuests(2), true);
    }

    @DisplayName("매장 주문 생성")
    @Test
    void createEatInOrder() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);

        assertAll(
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(eatInOrder.getOrderTable()).isEqualTo(orderTable)
        );
    }

    @DisplayName("매장 주문 빈 테이블이 아니면 에러")
    @Test
    void eatInOrderEmptyOrderTable() {
        orderTable.tableClear();

        assertThatThrownBy(() -> new EatInOrder(orderLineItems, orderTable)).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문 주문 수락")
    @Test
    void eatInOrderAccept() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);
        eatInOrder.accepted();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 상태의 주문만 수락")
    @Test
    void eatInOrderAcceptFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);
        eatInOrder.accepted();

        assertThatThrownBy(() -> eatInOrder.accepted()).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문 주문 서빙")
    @Test
    void eatInOrderServed() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);
        eatInOrder.accepted();
        eatInOrder.served();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수 상태의 주문만 서빙")
    @Test
    void eatInOrderServedFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);

        assertThatThrownBy(() -> eatInOrder.served()).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문 주문 완료")
    @Test
    void eatInOrderCompleted() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);
        eatInOrder.accepted();
        eatInOrder.served();
        eatInOrder.completed();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("서빙 완료 상태의 주문만 완료")
    @Test
    void eatInOrderCompletedFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);

        assertThatThrownBy(() -> eatInOrder.completed()).isInstanceOf(IllegalStateException.class);
    }
}
