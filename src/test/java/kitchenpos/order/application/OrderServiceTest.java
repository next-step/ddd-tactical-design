package kitchenpos.order.application;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menu.infra.InMemoryMenuRepository;
import kitchenpos.order.application.dto.OrderCreationRequest;
import kitchenpos.order.application.dto.OrderLineItemCreationRequest;
import kitchenpos.order.domain.DeliveryOrder;
import kitchenpos.order.domain.EatInOrder;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTableRepository;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.factory.OrderFactoryProvider;
import kitchenpos.order.infra.FakeKitchenridersClient;
import kitchenpos.order.infra.InMemoryOrderFactoryProvider;
import kitchenpos.order.infra.InMemoryOrderRepository;
import kitchenpos.order.infra.InMemoryOrderTableRepository;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private OrderFactoryProvider orderFactoryProvider;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        orderFactoryProvider = new InMemoryOrderFactoryProvider();
        orderService = new OrderService(orderRepository, menuRepository, orderTableRepository, kitchenridersClient, orderFactoryProvider);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        Menu menu = menu(19_000L, true, menuProduct());
        final UUID menuId = menuRepository.save(menu).getId();
        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.DELIVERY,
            List.of(orderLineCreationRequest(OrderType.DELIVERY, menuId, 19_000L, 3L)),
            "서울시 송파구 위례성대로 2",
            null
        );

        final DeliveryOrder actual = (DeliveryOrder) orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.type()),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1),
            () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.deliveryAddress())
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.TAKEOUT,
            List.of(orderLineCreationRequest(OrderType.TAKEOUT, menuId, 19_000L, 3L)),
            null,
            null
        );

        final Order actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.type()),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();

        final OrderTable orderTable = orderTable(4, true);
        orderTableRepository.save(orderTable);

        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.EAT_IN,
            List.of(orderLineCreationRequest(OrderType.EAT_IN, menuId, 19_000L, 3L)),
            null,
            orderTable.getId()
        );

        final EatInOrder actual = (EatInOrder) orderService.create(expected);

        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.type()),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1),
            () -> assertThat(actual.getOrderTable().getId()).isEqualTo(expected.orderTableId())
        );
    }

    @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final OrderType type) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderCreationRequest expected = orderCreationRequest(type, menuId);

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("orderLineItems")
    void create(final List<OrderLineItem> orderLineItems) {

        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.TAKEOUT,
            List.of(orderLineCreationRequest(OrderType.TAKEOUT, UUID.randomUUID(), 19_000L, 3L)),
            null,
            null
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return List.of(
            Arguments.of(List.of(orderLineCreationRequest(OrderType.TAKEOUT, INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderTable orderTable = orderTable(4, true);
        orderTableRepository.save(orderTable);

        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.EAT_IN,
            List.of(orderLineCreationRequest(OrderType.EAT_IN, menuId, 19_000L, quantity)),
            null,
            orderTable.getId()
        );

        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.TAKEOUT,
            List.of(orderLineCreationRequest(OrderType.TAKEOUT, menuId, 19_000L, quantity)),
            null,
            null
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();

        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.DELIVERY,
            List.of(orderLineCreationRequest(OrderType.DELIVERY, menuId, 19_000L, 3)),
            deliveryAddress,
            null
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(0, false)).getId();

        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.EAT_IN,
            List.of(orderLineCreationRequest(OrderType.EAT_IN, menuId, 19_000L, 3L)),
            null,
            orderTableId
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.TAKEOUT,
            List.of(orderLineCreationRequest(OrderType.TAKEOUT, menuId, 19_000L, 3L)),
            null,
            null
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();

        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.TAKEOUT,
            List.of(orderLineCreationRequest(OrderType.TAKEOUT, menuId, 16_000L, 3L)),
            null,
            null
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(eatInOrder(OrderStatus.WAITING, orderTable(4, true))).getId();
        final Order actual = orderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = orderRepository.save(eatInOrder(status, orderTable(4, true))).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID orderId = orderRepository.save(deliveryOrder(OrderStatus.WAITING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.accept(orderId);
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
            () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(takeoutOrder(OrderStatus.ACCEPTED)).getId();
        final Order actual = orderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final UUID orderId = orderRepository.save(deliveryOrder(OrderStatus.SERVED, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.startDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("배달 주문만 배달할 수 있다.")
    @Test
    void startDeliveryWithoutDeliveryOrder() {
        final UUID orderId = orderRepository.save(takeoutOrder(OrderStatus.SERVED)).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(deliveryOrder(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final UUID orderId = orderRepository.save(deliveryOrder(OrderStatus.DELIVERING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.completeDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(deliveryOrder(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> orderService.completeDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Order expected = orderRepository.save(deliveryOrder(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final Order actual = orderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(deliveryOrder(status, "서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final EatInOrder expected = eatInOrder(OrderStatus.SERVED, orderTable);
        orderRepository.save(expected);

        final Order actual = orderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        orderRepository.save(eatInOrder(OrderStatus.ACCEPTED, orderTable));
        final Order expected = eatInOrder(OrderStatus.SERVED, orderTable);
        orderRepository.save(expected);

        final Order actual = orderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(4)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        orderRepository.save(eatInOrder(OrderStatus.SERVED, orderTable));
        orderRepository.save(deliveryOrder(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
        final List<Order> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private static OrderLineItemCreationRequest orderLineCreationRequest(final OrderType orderType, final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemCreationRequest(orderType, menuId, BigDecimal.valueOf(price), quantity);
    }
}

