package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.infra.OrderTableClientImpl;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderPolicy;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.todo.domain.orders.OrderTableClient;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableRepository;
import kitchenpos.fixture.EatInOrderFixture;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.dto.OrderLineItemCreateRequest;
import kitchenpos.support.infra.MenuClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.EatInOrderFixture.eatInOrder;
import static kitchenpos.fixture.EatInOrderFixture.orderTable;
import static kitchenpos.fixture.Fixtures.INVALID_ID;
import static kitchenpos.fixture.MenuFixture.menu;
import static kitchenpos.fixture.MenuFixture.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EatInOrderServiceTest {
    private EatInOrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService orderService;
    private UUID orderTableId;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        MenuClient menuClient = new MenuClientImpl(menuRepository);
        orderTableRepository = new InMemoryOrderTableRepository();
        orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        OrderTableClient orderTableClient = new OrderTableClientImpl(orderTableRepository);
        EatInOrderPolicy eatInOrderPolicy = new EatInOrderPolicy(orderRepository, orderTableClient);
        orderService = new EatInOrderService(orderRepository, menuClient, orderTableClient, eatInOrderPolicy);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final EatInOrderCreateRequest expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final EatInOrderResponse actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id()).isNotNull(),
            () -> assertThat(actual.status()).isEqualTo(EatInOrderStatus.WAITING),
            () -> assertThat(actual.orderDateTime()).isNotNull(),
            () -> assertThat(actual.orderLineItems()).hasSize(1),
            () -> assertThat(actual.orderTableId()).isEqualTo(expected.orderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemCreateRequest> orderLineItems) {
        EatInOrderCreateRequest expected = createOrderRequest(orderTableId, orderLineItems);
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final EatInOrderCreateRequest expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity));
        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID unOccupiedOrderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final EatInOrderCreateRequest expected = createOrderRequest(unOccupiedOrderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final EatInOrderCreateRequest expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final EatInOrderCreateRequest expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(eatInOrder(EatInOrderStatus.WAITING, orderTable(true, 4))).getId();
        final EatInOrderResponse actual = orderService.accept(orderId);
        assertThat(actual.status()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final EatInOrderStatus status) {
        final UUID orderId = orderRepository.save(eatInOrder(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(EatInOrderFixture.eatInOrder(EatInOrderStatus.ACCEPTED)).getId();
        final EatInOrderResponse actual = orderService.serve(orderId);
        assertThat(actual.status()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final EatInOrderStatus status) {
        final UUID orderId = orderRepository.save(EatInOrderFixture.eatInOrder(status)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final EatInOrderStatus status) {
        final UUID orderId = orderRepository.save(EatInOrderFixture.eatInOrder(status)).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = orderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse actual = orderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.status()).isEqualTo(EatInOrderStatus.COMPLETED),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, orderTable));
        final EatInOrder expected = orderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse actual = orderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.status()).isEqualTo(EatInOrderStatus.COMPLETED),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(4)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final OrderTable orderTable2 = orderTableRepository.save(orderTable(true, 2));
        orderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTable));
        orderRepository.save(eatInOrder(EatInOrderStatus.WAITING, orderTable2));
        final List<EatInOrderResponse> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }


    private EatInOrderCreateRequest createOrderRequest(
        final UUID orderTableId,
        final OrderLineItemCreateRequest... orderLineItems
    ) {
        return new EatInOrderCreateRequest(List.of(orderLineItems), orderTableId);
    }

    private EatInOrderCreateRequest createOrderRequest(
        final UUID orderTableId,
        final List<OrderLineItemCreateRequest> orderLineItems
    ) {
        return new EatInOrderCreateRequest(orderLineItems, orderTableId);
    }

    private static OrderLineItemCreateRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemCreateRequest(menuId, BigDecimal.valueOf(price), quantity);
    }
}
