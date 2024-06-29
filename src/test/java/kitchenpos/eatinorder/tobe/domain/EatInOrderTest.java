package kitchenpos.eatinorder.tobe.domain;

import kitchenpos.eatinorder.tobe.domain.fixture.Fixture;
import kitchenpos.eatinorder.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorder.tobe.domain.order.EatInOrderStatus;
import kitchenpos.eatinorder.tobe.domain.order.OrderLineItem;
import kitchenpos.eatinorder.tobe.domain.order.OrderLineItems;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.menus.tobe.domain.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderTest {

    private OrderLineItem orderLineItem;
    private OrderLineItems orderLineItems;
    private OrderTable table;

    @BeforeEach
    void setUp() {
        Menu menu = Fixture.menuSetUP();
        table = Fixture.tableSetUP();
        orderLineItem = OrderLineItem.of(1, menu.getId(), BigDecimal.valueOf(menu.getMenuPrice()));
        orderLineItems = new OrderLineItems(List.of(orderLineItem));
    }

    @Test
    @DisplayName("매장주문을 접수할 수 있다.")
    void success1() {
        final var eatInOrder = EatInOrder.create(LocalDateTime.now(), orderLineItems, table.getId());

        assertAll(
                () -> assertThat(eatInOrder).isNotNull(),
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING)
        );


    }
}
