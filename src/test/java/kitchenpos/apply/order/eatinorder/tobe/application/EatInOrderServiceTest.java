package kitchenpos.apply.order.eatinorder.tobe.application;

import kitchenpos.apply.menus.tobe.domain.InMemoryMenuRepository;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import kitchenpos.apply.order.eatinorder.tobe.domain.InMemoryEatInOrderRepository;
import kitchenpos.apply.order.eatinorder.tobe.domain.InMemoryOrderTableRepository;
import kitchenpos.apply.order.eatinorders.tobe.application.EatInOrderService;
import kitchenpos.apply.order.eatinorders.tobe.application.OrderLineItemsFactory;
import kitchenpos.apply.order.eatinorders.tobe.domain.*;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderRequest;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderResponse;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.OrderLineItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static kitchenpos.apply.fixture.MenuFixture.*;
import static kitchenpos.apply.fixture.TobeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EatInOrderServiceTest {
    private EatInOrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        OrderLineItemsFactory orderLineItemsFactory = new OrderLineItemsFactory(menuRepository);
        orderService = new EatInOrderService(orderRepository, orderTableRepository, orderLineItemsFactory);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, true, menuProduct())).getId());
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(false, 4)).getId());
        final EatInOrderRequest request = eatInOrderRequest(orderTableId, orderLineItemRequest(menuId, 3L, 19_000L));
        final EatInOrderResponse response = orderService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
            () -> assertThat(response.getOrderDateTime()).isNotNull(),
            () -> assertThat(response.getOrderLineItemSequences()).hasSize(1),
            () -> assertThat(response.getOrderTableId()).isEqualTo(request.getOrderTableId().toString())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItemRequests")
    @ParameterizedTest
    void create(final List<OrderLineItemRequest> orderLineItemRequests) {
        final EatInOrderRequest request = eatInOrderRequest(orderLineItemRequests);
        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItemRequests() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(orderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, true, menuProduct())).getId());
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(false, 4)).getId());
        final EatInOrderRequest request = eatInOrderRequest(
            orderTableId, orderLineItemRequest(menuId, quantity, 19_000L)
        );
        assertDoesNotThrow(() -> orderService.create(request));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, true, menuProduct())).getId());
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(true, 0)).getId());
        final EatInOrderRequest request = eatInOrderRequest(
                orderTableId, orderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, false, menuProduct())).getId());
        final EatInOrderRequest request = eatInOrderRequest(orderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = UUID.fromString(menuRepository.save(menu(19_000L, true, menuProduct())).getId());
        final EatInOrderRequest expected = eatInOrderRequest(orderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = UUID.fromString(orderRepository.save(eatInOrder(EatInOrderStatus.WAITING, orderTable(true, 4))).getId());
        final EatInOrderResponse actual = orderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final EatInOrderStatus status) {
        final UUID orderId = UUID.fromString(orderRepository.save(eatInOrder(status, orderTable(true, 4))).getId());
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        EatInOrder order = eatInOrder(EatInOrderStatus.ACCEPTED);
        final UUID orderId = UUID.fromString(orderRepository.save(order).getId());
        final EatInOrderResponse actual = orderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final EatInOrderStatus status) {
        final UUID orderId = UUID.fromString(orderRepository.save(eatInOrder(status)).getId());
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder eatInOrder = orderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse response = orderService.complete(UUID.fromString(eatInOrder.getId()));
        assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }


    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final EatInOrderStatus status) {
        final UUID orderId = UUID.fromString(orderRepository.save(eatInOrder(status)).getId());
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = orderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse response = orderService.complete(UUID.fromString(expected.getId()));
        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
            () -> assertThat(getIsOccupied(orderTable)).isFalse(),
            () -> assertThat(getNumberOfGuests(orderTable)).isEqualTo(0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, orderTable));
        final EatInOrder request = orderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse response = orderService.complete(UUID.fromString(request.getId()));
        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
            () -> assertThat(getIsOccupied(orderTable)).isTrue(),
            () -> assertThat(getNumberOfGuests(orderTable)).isEqualTo(4)
        );
    }

    private int getNumberOfGuests(OrderTable orderTable) {
        return orderTableRepository.findById(UUID.fromString(orderTable.getId()))
                .orElseThrow().getNumberOfGuests();
    }

    private boolean getIsOccupied(OrderTable orderTable) {
        return orderTableRepository.findById(UUID.fromString(orderTable.getId()))
                .orElseThrow().isOccupied();
    }
}
