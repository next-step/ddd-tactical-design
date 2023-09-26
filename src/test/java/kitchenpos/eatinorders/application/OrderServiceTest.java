package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.EatInOrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.eatinorders.application.dto.OrderStatusResponse;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.tobe.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.NewFixtures.*;
import static kitchenpos.eatinorders.exception.OrderExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderServiceTest {
    private EatInOrderRepository orderRepository;
    private EatInMenuRepository menuRepository;
    private EatInOrderTableRepository orderTableRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryEatInMenuRepository();
        orderTableRepository = new InMemoryEatInOrderTableRepository();
        MockOrderEventService orderEventService = new MockOrderEventService(orderTableRepository);
        orderService = new OrderService(orderRepository, menuRepository, orderTableRepository, orderEventService);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(eatInMenu(19_000L, true)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, List.of(createOrderLineItemRequest(menuId, 19_000L, 3L)));
        final EatInOrderResponse actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(OrderType.EAT_IN),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getEatInOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTableId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @Test
    void create() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, List.of(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_FOUND_MENU);
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(eatInMenu(19_000L, true)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, List.of(createOrderLineItemRequest(menuId, 19_000L, quantity)));
        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(eatInMenu(19_000L, true)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, List.of(createOrderLineItemRequest(menuId, 19_000L, 3L)));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(NOT_OCCUPIED_ORDER_TABLE);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(eatInMenu(19_000L, false)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, List.of(createOrderLineItemRequest(menuId, 19_000L, 3L)));

        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_LINE_ITEM_MENU_NOT_DISPLAY);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(eatInMenu(39_000L, true)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, List.of(createOrderLineItemRequest(menuId, 19_000L, 3L)));

        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EQUALS_PRICE);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, orderTable(true, 4))).getId();
        final OrderStatusResponse actual = orderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(OrderStatus.ACCEPTED)).getId();
        final OrderStatusResponse actual = orderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_STATUS_NOT_ACCEPTED);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final EatInOrderTable eatInOrderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = orderRepository.save(order(OrderStatus.SERVED, eatInOrderTable));
        final OrderStatusResponse actual = orderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_STATUS_NOT_SERVED);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final EatInOrderTable eatInOrderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = orderRepository.save(order(OrderStatus.SERVED, eatInOrderTable));
        final OrderStatusResponse actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(eatInOrderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(orderTableRepository.findById(eatInOrderTable.getId()).get().getNumberOfGuests()).isEqualTo(NumberOfGuests.ZERO)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final EatInOrderTable eatInOrderTable = orderTableRepository.save(orderTable(true, 4));

        orderRepository.save(order(OrderStatus.SERVED, eatInOrderTable));
        final EatInOrder expected = orderRepository.save(order(OrderStatus.SERVED, eatInOrderTable));
        final OrderStatusResponse actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(eatInOrderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(orderTableRepository.findById(eatInOrderTable.getId()).get().getNumberOfGuests()).isEqualTo(NumberOfGuests.create(4))
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderRepository.save(order(OrderStatus.SERVED));
        orderRepository.save(order(OrderStatus.COMPLETED));
        final List<EatInOrderResponse> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private EatInOrderRequest createOrderRequest(final UUID orderTableId, final List<EatInOrderLineItemRequest> eatInOrderLineItemRequests) {
        return EatInOrderRequest.create(orderTableId, eatInOrderLineItemRequests);
    }

    private static EatInOrderLineItemRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return EatInOrderLineItemRequest.create(quantity, menuId, BigDecimal.valueOf(price));
    }
}
