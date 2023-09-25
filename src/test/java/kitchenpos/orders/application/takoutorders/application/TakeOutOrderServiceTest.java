package kitchenpos.orders.application.takoutorders.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.order.domain.*;
import kitchenpos.order.eatinorders.domain.OrderTableRepository;
import kitchenpos.order.takeoutorders.application.TakeOutOrderService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderAcceptService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCompleteService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCreateService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderServeService;
import kitchenpos.orders.application.InMemoryOrderRepository;
import kitchenpos.orders.application.InMemoryOrderTableRepository;
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

class TakeOutOrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private OrderLineItemsValidService orderLineItemsValidService;


    // TAKE_OUT order service
    private TakeOutOrderService takeOutOrderService;
    private TakeOutOrderCreateService takeOutOrderCreateService;
    private TakeOutOrderAcceptService takeOutOrderAcceptService;
    private TakeOutOrderServeService takeOutOrderServeService;
    private TakeOutOrderCompleteService takeOutOrderCompleteService;


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
        takeOutOrderCreateService = new TakeOutOrderCreateService(orderRepository, orderLineItemsValidService);
        takeOutOrderAcceptService = new TakeOutOrderAcceptService(orderRepository);
        takeOutOrderServeService = new TakeOutOrderServeService(orderRepository);
        takeOutOrderCompleteService = new TakeOutOrderCompleteService(orderRepository);
        takeOutOrderService = new TakeOutOrderService(orderRepository, takeOutOrderCreateService, takeOutOrderAcceptService, takeOutOrderServeService, takeOutOrderCompleteService);

    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final Order actual = takeOutOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems().getOrderLineItems()).hasSize(1)
        );
    }


    @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final OrderType type) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(type, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> takeOutOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        assertThatThrownBy(() -> {
            final Order expected = createOrderRequest(OrderType.TAKEOUT, orderLineItems);
            takeOutOrderService.create(expected);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();

        assertThatThrownBy(() -> {
            final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, quantity));
            takeOutOrderService.create(expected);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> takeOutOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> takeOutOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, orderTable(true, 4))).getId();
        final Order actual = takeOutOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> takeOutOrderService.accept(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(OrderStatus.ACCEPTED)).getId();
        final Order actual = takeOutOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> takeOutOrderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> takeOutOrderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.SERVED, orderTable));
        orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final List<Order> actual = takeOutOrderService.findAll();
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
}
