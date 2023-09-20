package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.vo.OrderLineItems;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EatInOrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private EatInOrderService eatInOrderService;
    private EatInOrderCreateService eatInOrderCreateService;
    private EatInOrderLineItemsService eatInOrderLineItemsService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        eatInOrderLineItemsService = new EatInOrderLineItemsService(menuRepository);

        eatInOrderCreateService = new EatInOrderCreateService(orderRepository, orderTableRepository, eatInOrderLineItemsService);
        eatInOrderService = new EatInOrderService(orderRepository, kitchenridersClient, eatInOrderCreateService);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(
                OrderType.DELIVERY, "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final Order actual = eatInOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems().getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final Order actual = eatInOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems().getOrderLineItems()).hasSize(1)
        );
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
        final Order expected = createOrderRequest(type, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<EatInOrderLineItem> eatInOrderLineItems) {
        assertThatThrownBy(() -> {
            final Order expected = createOrderRequest(OrderType.TAKEOUT, eatInOrderLineItems);
            eatInOrderService.create(expected);
        }).isInstanceOf(IllegalArgumentException.class);
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
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final Order expected = createOrderRequest(
                OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> eatInOrderService.create(expected));
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(
                OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(
                OrderType.DELIVERY, deliveryAddress, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
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
        final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 16_000L, 3L));
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

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = eatInOrderService.accept(orderId);
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED),
                () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
                () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
        );
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

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final UUID orderId = orderRepository.save(order(OrderStatus.SERVED, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = eatInOrderService.startDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("배달 주문만 배달할 수 있다.")
    @Test
    void startDeliveryWithoutDeliveryOrder() {
        final UUID orderId = orderRepository.save(order(OrderStatus.SERVED)).getId();
        assertThatThrownBy(() -> eatInOrderService.startDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> eatInOrderService.startDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final UUID orderId = orderRepository.save(order(OrderStatus.DELIVERING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = eatInOrderService.completeDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> eatInOrderService.completeDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Order expected = orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final Order actual = eatInOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> eatInOrderService.complete(orderId))
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
        orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final List<Order> actual = eatInOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private Order createOrderRequest(
            final OrderType type,
            final String deliveryAddress,
            final EatInOrderLineItem... eatInOrderLineItems
    ) {
        final Order order = new Order();
        order.setType(type);
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderLineItems(new OrderLineItems(Arrays.asList(eatInOrderLineItems)));
        return order;
    }

    private Order createOrderRequest(final OrderType orderType, final EatInOrderLineItem... eatInOrderLineItems) {
        return createOrderRequest(orderType, Arrays.asList(eatInOrderLineItems));
    }

    private Order createOrderRequest(final OrderType orderType, final List<EatInOrderLineItem> eatInOrderLineItems) {
        final Order order = new Order();
        order.setType(orderType);
        order.setOrderLineItems(new OrderLineItems(eatInOrderLineItems));
        order.setOrderTable(orderTable(false, 0));
        return order;
    }

    private Order createOrderRequest(
            final OrderType type,
            final UUID orderTableId,
            final EatInOrderLineItem... eatInOrderLineItems
    ) {
        final Order order = new Order();
        order.setType(type);
        order.setOrderTableId(orderTableId);
        order.setOrderLineItems(new OrderLineItems(Arrays.asList(eatInOrderLineItems)));
        return order;
    }

    private static EatInOrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem();
        eatInOrderLineItem.setSeq(new Random().nextLong());
        eatInOrderLineItem.setMenuId(menuId);
        eatInOrderLineItem.setPrice(BigDecimal.valueOf(price));
        eatInOrderLineItem.setQuantity(quantity);
        return eatInOrderLineItem;
    }
}
