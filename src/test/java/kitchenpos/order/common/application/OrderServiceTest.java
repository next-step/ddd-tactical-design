package kitchenpos.order.common.application;

import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderStatus;
import kitchenpos.order.common.model.OrderType;
import kitchenpos.order.common.repository.OrderRepository;
import kitchenpos.order.deliveryorder.infra.external.KitchenridersClient;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kitchenpos.TestFixtureFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private KitchenridersClient kitchenridersClient;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        menuRepository = mock(MenuRepository.class);
        orderTableRepository = mock(OrderTableRepository.class);
        kitchenridersClient = mock(KitchenridersClient.class);
        orderService = new OrderService(orderRepository, menuRepository, orderTableRepository, kitchenridersClient);
    }

    private Order createOrderRequestWithOccupiedTable(OrderType type, OrderStatus orderStatus, Menu menu,
                                                      String address) {
        OrderTable orderTable = createUsingOrderTable();
        return createOrder(createOrderLineItem(menu), orderTable, type,
                orderStatus, address);
    }

    private Order createOrderRequestWithEmptyTable(OrderType type, OrderStatus orderStatus, Menu menu,
                                                   String address) {
        OrderTable orderTable = createEmptyOrderTable();
        return createOrder(createOrderLineItem(menu), orderTable, type,
                orderStatus, address);
    }

    @Nested
    @DisplayName("공통 주문 관련")
    class CommonOrder {

        @Test
        @DisplayName("주문은 하나 이상의 주문 내역으로 생성할 수 있다")
        void create_order() {
            // given
            Menu menu = createMenuWithProductAndGroup();
            Order request = createOrderRequestWithEmptyTable(OrderType.DELIVERY, OrderStatus.WAITING, menu, "서울");
            when(menuRepository.findAllByIdIn(anyList())).thenReturn(List.of(menu));
            when(menuRepository.findById(any())).thenReturn(Optional.of(menu));
            when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // when
            Order result = orderService.create(request);

            // then
            assertThat(result.getOrderLineItems()).hasSize(1);
        }

        @Test
        @DisplayName("게시되지 않은 메뉴 주문 시 예외가 발생한다.")
        void menu_name_exception() {
            // given
            Menu menu = createMenuWithProductAndGroup(false);
            when(menuRepository.findAllByIdIn(anyList())).thenReturn(List.of(menu));
            when(menuRepository.findById(any())).thenReturn(Optional.of(menu));

            // when // then
            assertThatThrownBy(() -> {
                Order request = createOrderRequestWithEmptyTable(OrderType.DELIVERY, OrderStatus.WAITING, menu, "서울");
                orderService.create(request);
            }).isInstanceOf(IllegalStateException.class)
                    .hasMessage("주문 내역의 메뉴가 게시되어 있지 않습니다!");
        }
    }

    @Nested
    @DisplayName("배달 주문")
    class DeliveryOrderTest {

        @Test
        @DisplayName("배달 주문 시 주소가 없으면 예외가 발생한다.")
        void delivery_address_exception() {
            // given
            Menu menu = createMenuWithProductAndGroup();
            Order request = createOrderRequestWithEmptyTable(OrderType.DELIVERY, OrderStatus.SERVED, menu, null);
            when(menuRepository.findAllByIdIn(anyList())).thenReturn(List.of(menu));
            when(menuRepository.findById(any(UUID.class))).thenReturn(Optional.of(menu));

            // when // then
            assertThatThrownBy(() -> orderService.create(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("배달 주문 접수 시 배달 요청을 한다")
        void delivery_success() {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrder(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, OrderType.DELIVERY,
                    OrderStatus.WAITING, "서울");
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when
            orderService.accept(order.getId());

            // then
            verify(kitchenridersClient).requestDelivery(any(), any(), any());
        }

        @ParameterizedTest
        @EnumSource(value = OrderStatus.class, names = {"ACCEPTED", "SERVED", "DELIVERING", "DELIVERED", "COMPLETED"})
        @DisplayName("대기 중이 아닌 주문 시 예외가 발생한다.")
        void accept_exception(OrderStatus status) {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrderWithDeliveryType(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, status);
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when // then
            assertThatThrownBy(() -> orderService.accept(order.getId()))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("대기 중인 주문만 접수할 수 있다")
        void accept_success() {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrderWithDeliveryType(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, OrderStatus.WAITING);
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when
            Order result = orderService.accept(order.getId());

            // then
            assertThat(result.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        }

        @Test
        @DisplayName("배달 주문은 배달 상태와 배달 완료일 때 주문을 완료할 수 있다")
        void complete_order() {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrderWithDeliveryType(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, OrderStatus.DELIVERED);
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when
            Order result = orderService.complete(order.getId());

            // then
            assertThat(result.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        }
    }

    @Nested
    @DisplayName("포장 주문")
    class TakeOutOrderTest {

        @ParameterizedTest
        @EnumSource(value = OrderStatus.class, names = {"ACCEPTED", "SERVED", "DELIVERING", "DELIVERED", "COMPLETED"})
        @DisplayName("대기 중이 아닌 주문 시 예외가 발생한다.")
        void accept_exception(OrderStatus status) {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrderWithTakeOutType(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, status);
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when // then
            assertThatThrownBy(() -> orderService.accept(order.getId()))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("대기 중인 주문만 접수할 수 있다")
        void accept_success() {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrderWithTakeOutType(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, OrderStatus.WAITING);
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when
            Order result = orderService.accept(order.getId());

            // then
            assertThat(result.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        }

        @Test
        @DisplayName("포장 주문은 서빙이 완료 되면 주문을 완료할 수 있다")
        void complete_order() {
            // given
            OrderTable orderTable = createUsingOrderTable();
            Order order = createOrderWithTakeOutType(createOrderLineItem(createMenuWithProductAndGroup()),
                    orderTable, OrderStatus.SERVED);
            when(orderRepository.findById(any())).thenReturn(Optional.of(order));

            // when
            Order result = orderService.complete(order.getId());

            // then
            assertThat(result.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        }

        // 어떤 경우 든 통과함!
        // 포장 주문 기능 추가 시 여기에 추가
    }
}
