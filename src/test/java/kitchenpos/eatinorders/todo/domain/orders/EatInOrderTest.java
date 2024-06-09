package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.application.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.application.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.dto.EatInOrderCreateRequest;
import kitchenpos.support.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.infra.OrderTableClientImpl;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.domain.OrderLineItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kitchenpos.fixture.EatInOrderFixture.orderTable;
import static kitchenpos.fixture.MenuFixture.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("매장 주문")
class EatInOrderTest {
    private OrderTableClient orderTableClient;

    private OrderLineItemCreateRequest orderLineItem;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new InMemoryMenuRepository();
        OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
        orderTableClient = new OrderTableClientImpl(orderTableRepository);
        Menu menu = menuRepository.save(menu());
        orderLineItem = new OrderLineItemCreateRequest(menu.getId(), menu.getPrice(), 1L);
        orderTable = orderTableRepository.save(orderTable());
    }

    @DisplayName("매장 주문이 생성된다.")
    @Test
    void create() {
        EatInOrderCreateRequest request = createOrderRequest();
        OrderLineItems orderLineItems = OrderLineItems.from(request.orderLineItems());
        EatInOrder actual = EatInOrder.create(orderLineItems, request.orderTableId(), orderTableClient);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTableId()).isEqualTo(orderTable.getId())
        );
    }

    @DisplayName("매장 주문이 수락된다.")
    @Test
    void accept() {
        EatInOrderCreateRequest request = createOrderRequest();
        OrderLineItems orderLineItems = OrderLineItems.from(request.orderLineItems());
        EatInOrder order = new EatInOrder(EatInOrderStatus.WAITING, orderLineItems, request.orderTableId());

        EatInOrder actual = order.accept();

        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("매장 주문이 고객에게 서빙된다.")
    @Test
    void serve() {
        EatInOrderCreateRequest request = createOrderRequest();
        OrderLineItems orderLineItems = OrderLineItems.from(request.orderLineItems());
        EatInOrder order = new EatInOrder(EatInOrderStatus.ACCEPTED, orderLineItems, request.orderTableId());

        EatInOrder actual = order.serve();

        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }


    @DisplayName("매장 주문이 완료된다.")
    @Test
    void complete() {
        EatInOrderCreateRequest request = createOrderRequest();
        OrderLineItems orderLineItems = OrderLineItems.from(request.orderLineItems());
        EatInOrder order = new EatInOrder(EatInOrderStatus.SERVED, orderLineItems, request.orderTableId());

        EatInOrder actual = order.complete(eatInOrderPolicy());
        OrderTable orderTableActual = orderTableClient.getOrderTable(actual.getOrderTableId());

        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
                () -> assertThat(orderTableActual.isOccupied()).isFalse(),
                () -> assertThat(orderTableActual.getNumberOfGuests()).isZero()
        );
    }

    private EatInOrderCreateRequest createOrderRequest() {
        orderTable.sit();
        return new EatInOrderCreateRequest(List.of(orderLineItem), orderTable.getId());
    }

    private EatInOrderPolicy eatInOrderPolicy() {
        return new EatInOrderPolicy(new InMemoryEatInOrderRepository(), orderTableClient);
    }
}
