package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.application.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.infra.OrderTableClientImpl;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItems;
import kitchenpos.support.infra.MenuClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("매장 주문")
class EatInOrderTest {
    private MenuClient menuClient;
    private OrderTableClient orderTableClient;

    private OrderLineItemCreateRequest orderLineItem;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new InMemoryMenuRepository();
        OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
        menuClient = new MenuClientImpl(menuRepository);
        orderTableClient = new OrderTableClientImpl(orderTableRepository);
        Menu menu = menuRepository.save(menu());
        orderLineItem = new OrderLineItemCreateRequest(menu.getId(), menu.getPrice(), 1L);
        orderTable = orderTableRepository.save(orderTable());
    }

    @DisplayName("매장 주문이 생성된다.")
    @Test
    void create() {
        EatInOrderCreateRequest request = createOrderRequest();
        OrderLineItems orderLineItems = OrderLineItems.of(request.orderLineItems(), menuClient);
        EatInOrder actual = EatInOrder.create(orderLineItems, request.orderTableId(), orderTableClient);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTableId()).isEqualTo(orderTable.getId())
        );
    }

    private EatInOrderCreateRequest createOrderRequest() {
        orderTable.sit();
        return new EatInOrderCreateRequest(List.of(orderLineItem), orderTable.getId());
    }
}
