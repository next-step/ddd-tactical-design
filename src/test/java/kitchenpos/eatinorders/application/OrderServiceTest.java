package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.infra.FakeKitchenridersClient;
import kitchenpos.menus.domain.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static kitchenpos.eatinorders.domain.OrderStatus.*;
import static kitchenpos.fixture.MenuFixture.*;
import static kitchenpos.fixture.MenuGroupFixture.menuGroup;
import static kitchenpos.fixture.MenuProductFixture.menuProduct;
import static kitchenpos.fixture.OrderFixture.*;
import static kitchenpos.fixture.OrderTableFixture.orderTable;
import static kitchenpos.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        orderService = new OrderService(orderRepository, menuRepository, orderTableRepository, kitchenridersClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order expected = deliveryOrder(null, List.of(orderLineItem(menu, 3L)));
        final Order actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order expected = takeoutOrder(null, List.of(orderLineItem(menu, 3L)));
        final Order actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final Order expected = eatInOrder(orderTable, null, List.of(orderLineItem(menu, 3L)));
        final Order actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTable().getId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final OrderType type) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order expected = order(
                createOrderId(), now(), null,
                null, type, null, List.of(orderLineItem(menu, 3L))
        );
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final Order expected = takeoutOrder(null, orderLineItems);
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(orderLineItem(menu(menuGroup(), List.of(menuProduct(product()))), 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final Order expected = eatInOrder(orderTable, null, List.of(orderLineItem(menu, quantity)));
        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderLineItem orderLineItem = orderLineItem(menu, quantity);
        final Order takeoutOrder = takeoutOrder(null, List.of(orderLineItem));
        final Order deliveryOrder = deliveryOrder(null, List.of(orderLineItem));
        assertAll(
                () -> assertThatThrownBy(() -> orderService.create(takeoutOrder))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> orderService.create(deliveryOrder))
                        .isInstanceOf(IllegalArgumentException.class)
        );

    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order expected = deliveryOrder(deliveryAddress, null, List.of(orderLineItem(menu, 3L)));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderTable orderTable = orderTableRepository.save(orderTable(0, false));
        final Order expected = eatInOrder(orderTable, null, List.of(orderLineItem(menu, 3L)));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product())), false));
        final Order expected = takeoutOrder(ACCEPTED, List.of(orderLineItem(menu, 3L)));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final MenuProduct menuProduct = menuProduct(product());
        final BigDecimal originPrice = BigDecimal.valueOf(16_000);
        final BigDecimal difficultPrice = BigDecimal.valueOf(16_001);
        final Menu menu = menuRepository.save(
                menu(createMenuId(), CHICKEN_SET_MENU, originPrice, menuGroup(), List.of(menuProduct), true)
        );
        final OrderLineItem orderLineItem = orderLineItem(menu, 3L, difficultPrice);
        final Order expected = takeoutOrder(null, List.of(orderLineItem));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderTable orderTable = orderTable(4, true);
        final Order order = eatInOrder(orderTable, WAITING, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        final Order actual = orderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderTable orderTable = orderTable(4, true);
        final Order order = eatInOrder(orderTable, status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(WAITING, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        final Order actual = orderService.accept(orderId);
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(ACCEPTED),
                () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
                () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo(DELIVERY_ADDRESS)
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = takeoutOrder(ACCEPTED, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        final Order actual = orderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = takeoutOrder(status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(SERVED, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        final Order actual = orderService.startDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(DELIVERING);
    }

    @DisplayName("배달 주문만 배달할 수 있다.")
    @Test
    void startDeliveryWithoutDeliveryOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = takeoutOrder(SERVED, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(DELIVERING, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        final Order actual = orderService.completeDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.completeDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(DELIVERED, List.of(orderLineItem(menu)));
        final Order expected = orderRepository.save(order);
        final Order actual = orderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(COMPLETED);
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = deliveryOrder(status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutOrder(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = takeoutOrder(status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeEatInOrder(final OrderStatus status) {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final Order order = eatInOrder(orderTable(), status, List.of(orderLineItem(menu)));
        final UUID orderId = orderRepository.save(order).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final Order order = eatInOrder(orderTable, SERVED, List.of(orderLineItem(menu)));
        final Order expected = orderRepository.save(order);
        final Order actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderLineItem orderLineItem = orderLineItem(menu);
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        orderRepository.save(eatInOrder(orderTable, ACCEPTED, List.of(orderLineItem)));
        final Order expected = orderRepository.save(eatInOrder(orderTable, SERVED, List.of(orderLineItem)));
        final Order actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(4)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final Menu menu = menuRepository.save(menu(menuGroup(), List.of(menuProduct(product()))));
        final OrderLineItem orderLineItem = orderLineItem(menu);
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final Order eatInOrder = eatInOrder(orderTable, SERVED, List.of(orderLineItem));
        final Order deliveryOrder = deliveryOrder(DELIVERED, List.of(orderLineItem));
        orderRepository.save(eatInOrder);
        orderRepository.save(deliveryOrder);
        final List<Order> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }
}
