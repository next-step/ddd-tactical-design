package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.common.domain.model.OrderStatus;
import kitchenpos.common.domain.model.OrderType;
import kitchenpos.eatinorders.tobe.domain.fixture.OrderFixture;
import kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture;
import kitchenpos.eatinorders.tobe.domain.fixture.OrderTableFixture;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.validator.OrderLineItemValidator;
import kitchenpos.eatinorders.tobe.domain.validator.OrderTableValidator;
import kitchenpos.eatinorders.tobe.domain.validator.OrderValidator;
import kitchenpos.menus.tobe.domain.fixture.MenuFixture;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {

    private OrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;
    private MenuRepository menuRepository;
    private OrderLineItemValidator orderLineItemValidator;

    @BeforeEach
    void setUp() {
        this.orderRepository = new InMemoryOrderRepository();
        this.orderTableRepository = new InMemoryOrderTableRepository();
        this.menuRepository = new InMemoryMenuRepository();
        this.orderLineItemValidator = new OrderLineItemValidator(menuRepository);
    }

    @DisplayName("생성 검증")
    @Test
    void create() {
        OrderTable orderTable = orderTableRepository.save(OrderTableFixture.ORDER_TABLE_FIXTURE(new NumberOfGuests(2L)));
        orderTable.setUpTable();
        Assertions.assertDoesNotThrow(() -> makeEatInOrder(orderTable.getId()));
    }

    @DisplayName("수량 0 미만의 주문 생성시 매장 주문 외 에러 검증")
    @Test
    void createUnderZeroQuantity() {
        OrderTable orderTable = orderTableRepository.save(OrderTableFixture.ORDER_TABLE_FIXTURE(new NumberOfGuests(2L)));
        orderTable.setUpTable();

        assertThatThrownBy(() -> underZeroQuantityOrder(OrderType.DELIVERY, -1L, orderTable.getId()))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> underZeroQuantityOrder(OrderType.TAKEOUT, -1L, orderTable.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성 검증 - 빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createOrderAtEmptyTable() {
        OrderTable orderTable = orderTableRepository.save(OrderTableFixture.ORDER_TABLE_FIXTURE(new NumberOfGuests(2L)));
        assertThatThrownBy(() -> makeEatInOrder(orderTable.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("상태 변경 검증")
    @Test
    void advanceOrderStatusTest() {
        OrderTableValidator orderTableValidator = new OrderTableValidator(orderRepository, orderTableRepository);
        OrderTable orderTable = orderTableRepository.save(OrderTableFixture.ORDER_TABLE_FIXTURE(new NumberOfGuests(2L)));
        orderTable.setUpTable();
        Order order = makeEatInOrder(orderTable.getId());

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.WAITING);

        order.advanceOrderStatus(orderTableValidator);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);

        order.advanceOrderStatus(orderTableValidator);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SERVED);

        order.advanceOrderStatus(orderTableValidator);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);

        assertThatThrownBy(() -> order.advanceOrderStatus(orderTableValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 완료 처리시 해당 테이블 주문 전수 검사 후 모든 주문이 완료 상태일시 empty table 처리 여부 검증")
    @Test
    void emptyTableTest() {
        OrderTableValidator orderTableValidator = new OrderTableValidator(orderRepository, orderTableRepository);
        OrderTable orderTable = orderTableRepository.save(OrderTableFixture.ORDER_TABLE_FIXTURE(new NumberOfGuests(2L)));
        orderTable.setUpTable();
        Order order1 = makeEatInOrder(orderTable.getId());
        Order order2 = makeEatInOrder(orderTable.getId());

        completeOrder(orderTableValidator, order1);
        assertThat(orderTable.getNumberOfGuests().getValue()).isNotEqualTo(0L);
        assertThat(orderTable.isEmpty()).isFalse();

        completeOrder(orderTableValidator, order2);
        assertThat(orderTable.getNumberOfGuests().getValue()).isEqualTo(0L);
        assertThat(orderTable.isEmpty()).isTrue();
    }

    private Order makeEatInOrder(UUID orderTableId) {
        Menu menu = menuRepository.save(MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L));
        OrderLineItems orderLineItems = new OrderLineItems(Arrays.asList(OrderLineItemFixture.ORDER_LINE_ITEM_FIXTURE(19000L, 2L, menu.getId(), orderLineItemValidator)));
        return orderRepository.save(OrderFixture.ORDER_FIXTURE(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems, orderTableId, new OrderValidator(orderTableRepository)));
    }

    private Order underZeroQuantityOrder(OrderType orderType, Long quantity, UUID orderTableId) {
        Menu menu = menuRepository.save(MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L));
        OrderLineItems orderLineItems = new OrderLineItems(Arrays.asList(OrderLineItemFixture.ORDER_LINE_ITEM_FIXTURE(19000L, quantity, menu.getId(), orderLineItemValidator)));
        return orderRepository.save(OrderFixture.ORDER_FIXTURE(orderType, OrderStatus.WAITING, orderLineItems, orderTableId, new OrderValidator(orderTableRepository)));
    }

    private void completeOrder(OrderTableValidator orderTableValidator, Order order) {
        order.advanceOrderStatus(orderTableValidator); // WAITING -> ACCEPTED
        order.advanceOrderStatus(orderTableValidator); // ACCEPTED -> SERVED
        order.advanceOrderStatus(orderTableValidator); // SERVED -> COMPLETE
    }

}
