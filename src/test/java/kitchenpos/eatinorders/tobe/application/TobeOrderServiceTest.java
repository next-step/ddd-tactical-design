package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderCreateRequest;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderCreateResponse;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderLineItemRequest;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderResponse;
import kitchenpos.eatinorders.tobe.domain.TobeMenuClient;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderRepository;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import kitchenpos.menus.tobe.application.InMemoryTobeMenuRepository;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.TobeFixtures.menu;
import static kitchenpos.TobeFixtures.menuProduct;
import static kitchenpos.TobeFixtures.order;
import static kitchenpos.TobeFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TobeOrderServiceTest {
    private TobeOrderRepository orderRepository;
    private TobeMenuRepository menuRepository;
    private TobeOrderTableRepository orderTableRepository;
    private TobeMenuClient tobeMenuClient;
    private TobeOrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryTobeOrderRepository();
        menuRepository = new InMemoryTobeMenuRepository();
        orderTableRepository = new InMemoryTobeOrderTableRepository();
        tobeMenuClient = new InMemoryTobeMenuClient(menuRepository);
        orderService = new TobeOrderService(orderRepository, tobeMenuClient, orderTableRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final TobeOrderCreateRequest expected = createOrderRequest(orderTableId,
                                                                   createOrderLineItemRequest(menuId, 19_000L, 3L));
        final TobeOrderCreateResponse actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTableId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<TobeOrderLineItemRequest> orderLineItems) {
        TobeOrderCreateRequest expected = createOrderRequest(orderLineItems);
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
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        TobeOrderCreateRequest expected = createOrderRequest(orderTableId,
                                                             createOrderLineItemRequest(menuId, 19_000L, quantity));
        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final TobeOrderCreateRequest expected = createOrderRequest(
                orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final TobeOrderCreateRequest expected = createOrderRequest(createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TobeOrderCreateRequest expected = createOrderRequest(createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(OrderStatus.WAITING, orderTable(true, 4))).getId();
        final TobeOrderResponse actual = orderService.accept(orderId);
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
        final TobeOrderResponse actual = orderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final OrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> orderService.startDelivery(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
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
        final TobeOrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final TobeOrder expected = orderRepository.save(order(OrderStatus.SERVED, orderTable));
        final TobeOrderResponse actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(
                        0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final TobeOrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.ACCEPTED, orderTable));
        final TobeOrder expected = orderRepository.save(order(OrderStatus.SERVED, orderTable));
        final TobeOrderResponse actual = orderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(
                        4)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final TobeOrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(OrderStatus.SERVED, orderTable));
        orderRepository.save(order(OrderStatus.COMPLETED));
        final List<TobeOrderResponse> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private TobeOrderCreateRequest createOrderRequest(final TobeOrderLineItemRequest... orderLineItems) {
        return createOrderRequest(Arrays.asList(orderLineItems));
    }

    private TobeOrderCreateRequest createOrderRequest(List<TobeOrderLineItemRequest> orderLineItems) {
        return new TobeOrderCreateRequest(UUID.randomUUID(), OrderStatus.ACCEPTED, LocalDateTime.now(),
                                          orderLineItems,
                                          UUID.randomUUID());
    }


    private TobeOrderCreateRequest createOrderRequest(final UUID orderTableId, final TobeOrderLineItemRequest... orderLineItems) {
        return new TobeOrderCreateRequest(UUID.randomUUID(), OrderStatus.ACCEPTED, LocalDateTime.now(),
                                          Arrays.asList(orderLineItems), orderTableId);
    }

    private static TobeOrderLineItemRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new TobeOrderLineItemRequest(new Random().nextLong(), menuId, quantity, BigDecimal.valueOf(price));
    }
}
