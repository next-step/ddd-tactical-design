package kitchenpos.eatinorders.tobe.application;

import static kitchenpos.TobeFixtures.*;
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

import kitchenpos.common.tobe.domain.PurgomalumClient;
import kitchenpos.common.tobe.infra.FakePurgomalumClient;
import kitchenpos.eatinorders.tobe.application.adapter.EatInOrderMenuServiceAdapter;
import kitchenpos.eatinorders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.eatinorders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.eatinorders.tobe.application.dto.OrderLineItemCreationRequest;
import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.factory.OrderFactoryProvider;
import kitchenpos.eatinorders.tobe.infra.FakeKitchenridersClient;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderFactoryProvider;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderTableRepository;
import kitchenpos.menugroups.tobe.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.application.DefaultMenuService;
import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.menus.tobe.application.adapter.DefaultProductServiceAdapter;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuRepository;
import kitchenpos.products.tobe.application.DefaultProductService;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.application.adapter.ProductsMenuServiceAdapter;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;

class OrderServiceTest {
    private OrderRepository orderRepository;

    private MenuRepository menuRepository;

    private MenuService menuService;
    private MenuServiceAdapter menuServiceAdapter;

    private ProductService productService;

    private PurgomalumClient purgomalumClient;

    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private OrderFactoryProvider orderFactoryProvider;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        productService = new DefaultProductService(new InMemoryProductRepository(), new ProductsMenuServiceAdapter(menuService), purgomalumClient);
        menuService = new DefaultMenuService(menuRepository, new InMemoryMenuGroupRepository(), new DefaultProductServiceAdapter(productService), purgomalumClient);
        menuServiceAdapter = new EatInOrderMenuServiceAdapter(menuService);
        kitchenridersClient = new FakeKitchenridersClient();
        purgomalumClient = new FakePurgomalumClient();
        orderFactoryProvider = new InMemoryOrderFactoryProvider();
        orderService = new OrderService(orderRepository, menuServiceAdapter, orderTableRepository, kitchenridersClient, orderFactoryProvider);
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
        orderRepository.save(eatInOrder(OrderStatus.DELIVERED, orderTable));
        final List<Order> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private static OrderLineItemCreationRequest orderLineCreationRequest(final OrderType orderType, final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemCreationRequest(orderType, menuId, BigDecimal.valueOf(price), quantity);
    }
}

