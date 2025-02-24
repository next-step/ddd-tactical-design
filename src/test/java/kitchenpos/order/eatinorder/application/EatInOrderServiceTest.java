package kitchenpos.order.eatinorder.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.orderTable;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.ACCEPTED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.COMPLETED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.SERVED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menu.infra.InMemoryMenuRepository;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderLineItem;
import kitchenpos.order.common.domain.OrderRepository;
import kitchenpos.order.common.infra.InMemoryOrderRepository;
import kitchenpos.order.eatinorder.domain.EatInOrder;
import kitchenpos.order.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.OrderTable;
import kitchenpos.order.eatinorder.domain.OrderTableRepository;
import kitchenpos.order.eatinorder.infra.InMemoryOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class EatInOrderServiceTest {

    private OrderRepository orderRepository;
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService eatInOrderService;

    @BeforeEach
    void setUp() {
        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        orderRepository = inMemoryOrderRepository;
        eatInOrderRepository = inMemoryOrderRepository;
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        eatInOrderService = new DefaultEatInOrderService(orderRepository, eatInOrderRepository, menuRepository,
            orderTableRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrder expected = createOrderRequest(orderTableId,
            createOrderLineItemRequest(menuId, 19_000L, 3L));
        final EatInOrder actual = (EatInOrder) eatInOrderService.create(expected);
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

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final Order expected = createOrderRequest(orderTableId, orderLineItems);
        assertThatThrownBy(() -> eatInOrderService.create(expected))
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
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final Order expected = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> eatInOrderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final Order expected = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final Order expected = createOrderRequest(orderTableId,
            createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final Order expected = createOrderRequest(orderTableId,
            createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(WAITING, orderTable(true, 4)))
            .getId();
        final EatInOrder actual = (EatInOrder) eatInOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final EatInOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> eatInOrderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(ACCEPTED, orderTable(true, 4))).getId();
        final EatInOrder actual = (EatInOrder) eatInOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final EatInOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
        assertThatThrownBy(() -> eatInOrderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeEatInOrder(final EatInOrderStatus status) {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderId = orderRepository.save(order(status, orderTable)).getId();
        assertThatThrownBy(() -> eatInOrderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final Order expected = orderRepository.save(order(SERVED, orderTable));
        final EatInOrder actual = (EatInOrder) eatInOrderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(COMPLETED),
            () -> assertThat(
                orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
            () -> assertThat(
                orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(
                0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(ACCEPTED, orderTable));
        final Order expected = orderRepository.save(order(SERVED, orderTable));
        final EatInOrder actual = (EatInOrder) eatInOrderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(COMPLETED),
            () -> assertThat(
                orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
            () -> assertThat(
                orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(
                4)
        );
    }

    private EatInOrder createOrderRequest(
        final UUID orderTableId,
        final OrderLineItem... orderLineItems
    ) {
        return createOrderRequest(orderTableId, Arrays.asList(orderLineItems));
    }

    private EatInOrder createOrderRequest(
        final UUID orderTableId,
        final List<OrderLineItem> orderLineItems
    ) {
        final EatInOrder order = new EatInOrder();
        order.setOrderTableId(orderTableId);
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    private static OrderLineItem createOrderLineItemRequest(final UUID menuId, final long price,
        final long quantity) {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenuId(menuId);
        orderLineItem.setPrice(BigDecimal.valueOf(price));
        orderLineItem.setQuantity(quantity);
        return orderLineItem;
    }
}
