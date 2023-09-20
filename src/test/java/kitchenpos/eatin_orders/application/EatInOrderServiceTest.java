package kitchenpos.eatin_orders.application;

import kitchenpos.eatin_orders.infrastructure.InMemoryEatInOrderRepository;
import kitchenpos.eatin_orders.infrastructure.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.application.orders.EatInOrderService;
import kitchenpos.eatinorders.domain.orders.EatInOrder;
import kitchenpos.eatinorders.domain.orders.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.domain.orders.OrderStatus;
import kitchenpos.eatinorders.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.Fixtures.*;
import static kitchenpos.eatin_orders.EatInOrderFixtures.order;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EatInOrderServiceTest {
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private ProductRepository productRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService eatInOrderService;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        eatInOrderService = new EatInOrderService(eatInOrderRepository, menuRepository, orderTableRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, productRepository)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrder expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final EatInOrder actual = eatInOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTable().getId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<EatInOrderLineItem> orderLineItems) {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrder expected = createOrderRequest(orderTableId, orderLineItems);
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
        final UUID menuId = menuRepository.save(menu(19_000L, true, productRepository)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrder expected = createOrderRequest(
                orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> eatInOrderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, productRepository)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final EatInOrder expected = createOrderRequest(
                orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, productRepository)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final EatInOrder expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, productRepository)).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();

        final EatInOrder expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderId = eatInOrderRepository.save(order(OrderStatus.WAITING, orderTable(true, 4), productRepository)).getId();
        final EatInOrder actual = eatInOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = eatInOrderRepository.save(order(status, orderTable(true, 4), productRepository)).getId();
        assertThatThrownBy(() -> eatInOrderService.accept(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderId = eatInOrderRepository.save(order(OrderStatus.ACCEPTED, orderTable, productRepository)).getId();
        final EatInOrder actual = eatInOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

        final UUID orderId = eatInOrderRepository.save(order(status, orderTable, productRepository)).getId();
        assertThatThrownBy(() -> eatInOrderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(order(OrderStatus.SERVED, orderTable, productRepository));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final OrderStatus status) {
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderId = eatInOrderRepository.save(order(status, orderTable, productRepository)).getId();
        assertThatThrownBy(() -> eatInOrderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(order(OrderStatus.SERVED, orderTable, productRepository));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(NumberOfGuests.ZERO)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        eatInOrderRepository.save(order(OrderStatus.ACCEPTED, orderTable, productRepository));
        final EatInOrder expected = eatInOrderRepository.save(order(OrderStatus.SERVED, orderTable, productRepository));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(new NumberOfGuests(4))
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        eatInOrderRepository.save(order(OrderStatus.SERVED, orderTable, productRepository));
        eatInOrderRepository.save(order(OrderStatus.COMPLETED, orderTable, productRepository));
        final List<EatInOrder> actual = eatInOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private EatInOrder createOrderRequest(final UUID orderTableId, final List<EatInOrderLineItem> orderLineItems) {
        final EatInOrder order = new EatInOrder();
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    private EatInOrder createOrderRequest(
            final UUID orderTableId,
            final EatInOrderLineItem... orderLineItems
    ) {
        final EatInOrder order = new EatInOrder();
        order.setOrderTableId(orderTableId);
        order.setOrderLineItems(Arrays.asList(orderLineItems));
        return order;
    }

    private static EatInOrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        final EatInOrderLineItem orderLineItem = new EatInOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenuId(menuId);
        orderLineItem.setPrice(BigDecimal.valueOf(price));
        orderLineItem.setQuantity(quantity);
        return orderLineItem;
    }
}
