package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.domain.tobe.domain.*;
import kitchenpos.eatinorders.domain.tobe.domain.vo.Quantity;
import kitchenpos.eatinorders.domain.tobe.domain.vo.TableEmptyStatus;
import kitchenpos.eatinorders.dto.EatInOrderRegisterRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.dto.OrderStatusChangeRequest;
import kitchenpos.menus.application.InMemoryTobeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EatInOrderServiceTest {
    private EatInOrderRepository orderRepository;
    private TobeMenuRepository menuRepository;
    private TobeOrderTableRepository orderTableRepository;
    private EatInOrderCompleteService orderCompleteService;
    private EatInOrderService orderService;

    private static List<Arguments> orderLineItemsArgument() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList())
        );
    }

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryTobeMenuRepository();
        orderTableRepository = new InMemoryTobeOrderTableRepository();
        orderCompleteService = new EatInOrderCompleteService(orderRepository, orderTableRepository);
        orderService = new EatInOrderService(orderRepository, menuRepository, orderTableRepository, orderCompleteService);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        //given
        final TobeMenu menu = menuRepository.save(tobeMenu("후라이드",17_000));
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        final EatInOrderRegisterRequest request = new EatInOrderRegisterRequest(Arrays.asList(tobeOrderLineItem(menu, Quantity.One())), table.getId());
        //when
        final EatInOrderResponse response = orderService.create(request);
        //then
        assertThat(response).isNotNull();
        assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(response.getOrderDateTime()).isNotNull(),
                () -> assertThat(response.getOrderLineItems()).hasSize(1),
                () -> assertThat(response.getTableId()).isEqualTo(table.getId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItemsArgument")
    @ParameterizedTest
    void create(final List<TobeOrderLineItem> orderLineItems) {
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        final EatInOrderRegisterRequest request = new EatInOrderRegisterRequest(orderLineItems, table.getId());
        assertThatThrownBy(() -> orderService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final TobeMenu menu = menuRepository.save(tobeMenu("후라이드",17_000));
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        final EatInOrderRegisterRequest request = new EatInOrderRegisterRequest(Arrays.asList(tobeOrderLineItem(menu, Quantity.of(quantity))), table.getId());
        assertDoesNotThrow(() -> orderService.create(request));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        //given
        final TobeMenu menu = menuRepository.save(tobeMenu("후라이드",17_000));
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 0, TableEmptyStatus.EMPTY));
        final EatInOrderRegisterRequest request = new EatInOrderRegisterRequest(Arrays.asList(tobeOrderLineItem(menu, Quantity.One())), table.getId());
        //when&&then
        assertThatThrownBy(() -> orderService.create(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        //given
        final TobeMenu menu = menuRepository.save(tobeMenu("후라이드",17_000, false));
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        final EatInOrderRegisterRequest request = new EatInOrderRegisterRequest(Arrays.asList(tobeOrderLineItem(menu, Quantity.One())), table.getId());
        //when&&then
        assertThatThrownBy(() -> orderService.create(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        //given
        final TobeMenu menu = menuRepository.save(tobeMenu("후라이드",17_000));
        final TobeMenu menu2 = tobeMenu(menu.getId(),"후라이드",18_000);

        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        final EatInOrderRegisterRequest request = new EatInOrderRegisterRequest(Arrays.asList(tobeOrderLineItem(menu2, Quantity.One())), table.getId());
        //when&&then
        assertThatThrownBy(() -> orderService.create(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items,table));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.ACCEPTED, OrderType.EAT_IN);
        //when
        EatInOrderResponse response = orderService.accept(request);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatus().isAccepted()).isTrue();
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items,table, status));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.ACCEPTED, OrderType.EAT_IN);
        //when&&then
        assertThatThrownBy(() -> orderService.accept(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.ACCEPTED));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.SERVED, OrderType.EAT_IN);
        //when
        EatInOrderResponse response = orderService.serve(request);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatus().isServed()).isTrue();
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, status));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.SERVED, OrderType.EAT_IN);
        //when&&then
        assertThatThrownBy(() -> orderService.serve(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.ACCEPTED));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.SERVED, OrderType.EAT_IN);
        //when
        EatInOrderResponse response = orderService.serve(request);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatus().isServed()).isTrue();
    }

    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeEatInOrder(final OrderStatus status) {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, status));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.SERVED, OrderType.EAT_IN);
        //when&&then
        assertThatThrownBy(() -> orderService.complete(request))
                .isInstanceOf(IllegalStateException.class);

    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.SERVED));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.COMPLETED, OrderType.EAT_IN);
        //when
        EatInOrderResponse response = orderService.complete(request);
        //then
        assertThat(response).isNotNull();
        assertAll(
                () -> assertThat(response.getStatus().isCompleted()).isTrue(),
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        //given
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.SERVED));
        EatInOrder order2 = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.ACCEPTED));
        OrderStatusChangeRequest request = new OrderStatusChangeRequest(order.getId(), OrderStatus.COMPLETED, OrderType.EAT_IN);
        //when
        EatInOrderResponse response = orderService.complete(request);
        //then
        assertThat(response).isNotNull();
        assertAll(
                () -> assertThat(table.isEmpty()).isFalse(),
                () -> assertThat(table.getNumberOfGuests()).isEqualTo(2)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderLineItems items = orderLineItems();
        final TobeOrderTable table = orderTableRepository.save(tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED));
        EatInOrder order = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.SERVED));
        EatInOrder order2 = orderRepository.save(EatInOrder.Of(items, table, OrderStatus.ACCEPTED));
        final List<EatInOrderResponse> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }
}
