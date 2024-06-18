package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.domain.eatinorder.*;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;
import kitchenpos.eatinorders.domain.menu.MenuClient;
import kitchenpos.eatinorders.infra.DefaultMenuClient;
import kitchenpos.eatinorders.infra.FakeKitchenridersClient;
import kitchenpos.eatinorders.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.infra.InMemoryOrderTableRepository;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private OrderService orderService;
    private MenuRepository menuRepository;
    private MenuClient menuClient;
    private ClearOrderTableService clearOrderTableService;
    private PassToRiderService passToRiderService;
    private OrderFactory orderFactory;
    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();

        menuClient = new DefaultMenuClient(menuRepository);
        clearOrderTableService = new ClearOrderTableService(orderRepository, orderTableRepository);
        passToRiderService = new PassToRiderService(kitchenridersClient);
        orderFactory = new DefaultOrderFactory();

        orderService = new OrderService(orderRepository, passToRiderService, clearOrderTableService,
                menuClient, orderFactory);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(
            OrderType.DELIVERY, OrderStatus.WAITING ,"서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final Order actual = orderService.create(expected);
        final DeliveryOrder deliveryOrder = (DeliveryOrder) actual;
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(deliveryOrder.getId()).isNotNull(),
            () -> assertThat(deliveryOrder.isDelivery()).isTrue(),
            () -> assertThat(deliveryOrder.hasStatus(OrderStatus.WAITING)).isTrue(),
            () -> assertThat(deliveryOrder.getOrderLineItemsSum()).isEqualTo(BigDecimal.valueOf(57_000L)),
            () -> assertThat(deliveryOrder.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final Order actual = orderService.create(expected);
        final TakeoutOrder eatInOrder = (TakeoutOrder) actual;

        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(eatInOrder.getId()).isNotNull(),
            () -> assertThat(eatInOrder.isNotEatIn()).isTrue(),
            () -> assertThat(eatInOrder.hasStatus(OrderStatus.WAITING)).isTrue(),
            () -> assertThat(eatInOrder.getOrderLineItemsSum()).isEqualTo(BigDecimal.valueOf(57_000L))
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final OrderRequest expected = createOrderRequest(OrderType.EAT_IN, orderTable, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final Order actual = orderService.create(expected);
        final EatInOrder eatInOrder = (EatInOrder) actual;

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(eatInOrder.getId()).isNotNull(),
                () -> assertThat(eatInOrder.isNotEatIn()).isFalse(),
                () -> assertThat(eatInOrder.hasStatus(OrderStatus.WAITING)).isTrue(),
                () -> assertThat(eatInOrder.getOrderLineItemsSum()).isEqualTo(BigDecimal.valueOf(57_000L)),
                () -> assertThat(eatInOrder.getOrderTable()).isEqualTo(orderTable)
        );
    }

    @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final OrderType type) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(type, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemRequest> orderLineItemRequests) {
        final OrderRequest expected = createOrderRequest(OrderType.TAKEOUT, orderLineItemRequests);
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final OrderRequest expected = createOrderRequest(
            OrderType.EAT_IN, orderTable, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(
            OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(
            OrderType.DELIVERY, OrderStatus.WAITING, deliveryAddress, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 0));
        final OrderRequest expected = createOrderRequest(
            OrderType.EAT_IN, orderTable, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderRequest expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, orderTable(true, 4))).getId();
        final Order actual = orderService.accept(orderId);
        assertThat(actual.hasStatus(OrderStatus.ACCEPTED)).isTrue();
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.accept(orderId);
        assertAll(
            () -> assertThat(actual.hasStatus(OrderStatus.ACCEPTED)).isTrue(),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
            () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(OrderStatus.ACCEPTED)).getId();
        final Order actual = orderService.serve(orderId);
        assertThat(actual.hasStatus(OrderStatus.SERVED)).isTrue();
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final UUID orderId = orderRepository.save(order(OrderStatus.SERVED, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.startDelivery(orderId);
        assertThat(actual.hasStatus(OrderStatus.DELIVERING)).isTrue();
    }

    @DisplayName("배달 주문만 배달할 수 있다.")
    @Test
    void startDeliveryWithoutDeliveryOrder() {
        final UUID orderId = orderRepository.save(order(OrderStatus.SERVED)).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final UUID orderId = orderRepository.save(order(OrderStatus.DELIVERING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.completeDelivery(orderId);
        assertThat(actual.hasStatus(OrderStatus.DELIVERED)).isTrue();
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> orderService.completeDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Order expected = orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final Order actual = orderService.complete(expected.getId());
        assertThat(actual.hasStatus(OrderStatus.COMPLETED)).isTrue();
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTableRequest = orderTableRepository.save(orderTable(true, 4));
        final Order expected = orderRepository.save(order(OrderStatus.SERVED,
                orderTableRequest));
        final Order actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.hasStatus(OrderStatus.COMPLETED)).isTrue(),
                () -> assertThatThrownBy(() -> actual.getOrderTable().changeCustomerHeadCounts(3))
                        .isInstanceOf(IllegalStateException.class)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTableRequest = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.ACCEPTED, orderTableRequest));
        final Order expected = orderRepository.save(order(OrderStatus.SERVED,
            orderTableRequest));

        assertAll(
                () -> assertThatThrownBy(() -> orderService.complete(expected.getId()))
                        .isInstanceOf(IllegalStateException.class)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTableRequest = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.SERVED, orderTableRequest));
        orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final List<Order> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private OrderRequest createOrderRequest(
        final OrderType type,
        final OrderStatus orderStatus,
        final String deliveryAddress,
        final OrderLineItemRequest... orderLineItemRequests
    ) {
        final OrderRequest orderRequest = new OrderRequest();
        orderRequest.setType(type);
        orderRequest.setStatus(orderStatus);
        orderRequest.setDeliveryAddress(deliveryAddress);
        orderRequest.setOrderLineItems(Arrays.asList(orderLineItemRequests));
        return orderRequest;
    }

    private OrderRequest createOrderRequest(final OrderType orderType, final OrderLineItemRequest... orderLineItemRequests) {
        return createOrderRequest(orderType, Arrays.asList(orderLineItemRequests));
    }

    private OrderRequest createOrderRequest(final OrderType orderType, final List<OrderLineItemRequest> orderLineItemRequests) {
        final OrderRequest orderRequest = new OrderRequest();
        orderRequest.setType(orderType);
        orderRequest.setStatus(OrderStatus.WAITING);
        orderRequest.setOrderLineItems(orderLineItemRequests);
        return orderRequest;
    }

    private OrderRequest createOrderRequest(
        final OrderType type,
        final OrderTable orderTable,
        final OrderLineItemRequest... orderLineItemRequests
    ) {
        final OrderRequest orderRequest = new OrderRequest();
        orderRequest.setType(type);
        orderRequest.setStatus(OrderStatus.WAITING);
        orderRequest.setOrderTable(orderTable);
        orderRequest.setOrderLineItems(Arrays.asList(orderLineItemRequests));
        return orderRequest;
    }

    private static OrderLineItemRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        final OrderLineItemRequest orderLineItemRequest = new OrderLineItemRequest();
        orderLineItemRequest.setSeq(new Random().nextLong());
        orderLineItemRequest.setMenuId(menuId);
        orderLineItemRequest.setPrice(BigDecimal.valueOf(price));
        orderLineItemRequest.setQuantity(quantity);
        return orderLineItemRequest;
    }
}
