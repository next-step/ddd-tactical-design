package kitchenpos.orders.eatinorders.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.order.domain.*;
import kitchenpos.order.eatinorders.application.EatInOrderService;
import kitchenpos.order.eatinorders.domain.*;
import kitchenpos.orders.InMemoryOrderRepository;
import kitchenpos.orders.eatinorders.infra.InMemoryOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EatInOrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private OrderLineItemsValidService orderLineItemsValidService;


    // EAT_IN order service
    private EatInOrderService eatInOrderService;
    private EatInOrderCreateService eatInOrderCreateService;
    private EatInOrderAcceptService eatInOrderAcceptService;
    private EatInOrderServeService eatInOrderServeService;
    private EatInOrderCompleteService eatInOrderCompleteService;

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    private static OrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItem(menuId, quantity, BigDecimal.valueOf(price));
    }

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        orderLineItemsValidService = new OrderLineItemsValidService(menuRepository);

        eatInOrderCreateService = new EatInOrderCreateService(orderTableRepository, orderLineItemsValidService);
        eatInOrderAcceptService = new EatInOrderAcceptService(orderRepository);
        eatInOrderServeService = new EatInOrderServeService(orderRepository);
        eatInOrderCompleteService = new EatInOrderCompleteService(orderRepository, new OrderTableClearService(orderTableRepository, orderRepository));
        eatInOrderService = new EatInOrderService(orderRepository, eatInOrderCreateService, eatInOrderAcceptService, eatInOrderServeService, eatInOrderCompleteService);

    }


    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final Order expected = createOrderRequest(OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final Order actual = eatInOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems().getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTable().getId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final OrderType type) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final Order expected = createOrderRequest(type, orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        assertThatThrownBy(() -> {
            final Order expected = createOrderRequest(OrderType.EAT_IN, orderTableId, orderLineItems);
            eatInOrderService.create(expected);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final Order expected = createOrderRequest(
                OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> eatInOrderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final Order expected = createOrderRequest(
                OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.EAT_IN, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.EAT_IN, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, orderTable(true, 4))).getId();
        final Order actual = eatInOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> eatInOrderService.accept(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(OrderStatus.ACCEPTED)).getId();
        final Order actual = eatInOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> eatInOrderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> eatInOrderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final Order expected = orderRepository.save(order(OrderStatus.SERVED, orderTable));
        final Order actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.ACCEPTED, orderTable));
        final Order expected = orderRepository.save(order(OrderStatus.SERVED, orderTable));
        final Order actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(4)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.SERVED, orderTable));
        orderRepository.save(order(OrderStatus.COMPLETED, "서울시 송파구 위례성대로 2"));
        final List<Order> actual = eatInOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private Order createOrderRequest(
            final OrderType type,
            final String deliveryAddress,
            final OrderLineItem... orderLineItems
    ) {
        return new Order(UUID.randomUUID(), type, OrderStatus.WAITING, LocalDateTime.now(), new OrderLineItems(Arrays.asList(orderLineItems)), deliveryAddress, null, null);
    }

    private Order createOrderRequest(final OrderType orderType, final OrderLineItem... orderLineItems) {
        return createOrderRequest(orderType, Arrays.asList(orderLineItems));
    }

    private Order createOrderRequest(final OrderType orderType, final List<OrderLineItem> orderLineItems) {
        OrderTable orderTable = orderTable(false, 0);
        return new Order(UUID.randomUUID(), orderType, OrderStatus.WAITING, LocalDateTime.now(), new OrderLineItems(orderLineItems), null, orderTable, orderTable.getId());
    }

    private Order createOrderRequest(
            final OrderType type,
            final UUID orderTableId,
            final OrderLineItem... orderLineItems
    ) {
        return new Order(UUID.randomUUID(), type, OrderStatus.WAITING, LocalDateTime.now(), new OrderLineItems(Arrays.asList(orderLineItems)), null, null, orderTableId);
    }

    private Order createOrderRequest(
            final OrderType type,
            final UUID orderTableId,
            final List<OrderLineItem> orderLineItems
    ) {
        return new Order(UUID.randomUUID(), type, OrderStatus.WAITING, LocalDateTime.now(), new OrderLineItems(orderLineItems), null, null, orderTableId);
    }
}
