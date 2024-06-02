package kitchenpos.orders.tobe.application;

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

import kitchenpos.common.tobe.infra.TestContainer;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.orders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.orders.tobe.application.dto.OrderLineItemCreationRequest;
import kitchenpos.orders.tobe.domain.Order;
import kitchenpos.orders.tobe.domain.OrderLineItem;
import kitchenpos.orders.tobe.domain.OrderRepository;
import kitchenpos.orders.tobe.domain.OrderStatus;
import kitchenpos.orders.tobe.domain.OrderTable;
import kitchenpos.orders.tobe.domain.OrderTableRepository;
import kitchenpos.orders.tobe.domain.OrderType;
import kitchenpos.orders.tobe.infra.FakeKitchenridersClient;

class DeliveryOrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private OrderService orderService;
    private FakeKitchenridersClient kitchenridersClient;

    @BeforeEach
    void setUp() {
        TestContainer testContainer = new TestContainer();
        this.orderRepository = testContainer.orderRepository;
        this.menuRepository = testContainer.menuRepository;
        this.orderTableRepository = testContainer.orderTableRepository;
        this.orderService = testContainer.deliveryOrderService;
        this.kitchenridersClient = testContainer.kitchenridersClient;
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
            OrderType.DELIVERY,
            List.of(orderLineCreationRequest(OrderType.DELIVERY, UUID.randomUUID(), 19_000L, 3L)),
            null,
            null
        );

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return List.of(
            Arguments.of(List.of(orderLineCreationRequest(OrderType.DELIVERY, INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.DELIVERY,
            List.of(orderLineCreationRequest(OrderType.DELIVERY, menuId, 19_000L, quantity)),
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

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final OrderCreationRequest expected = new OrderCreationRequest(
            OrderType.DELIVERY,
            List.of(orderLineCreationRequest(OrderType.DELIVERY, menuId, 19_000L, 3L)),
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
            OrderType.DELIVERY,
            List.of(orderLineCreationRequest(OrderType.DELIVERY, menuId, 16_000L, 3L)),
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

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID orderId = orderRepository.save(deliveryOrder(OrderStatus.WAITING, "서울시 송파구 위례성대로 2")).getId();
        final Order actual = orderService.accept(orderId);
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
            () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
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

    private static OrderLineItemCreationRequest orderLineCreationRequest(
        final OrderType orderType,
        final UUID menuId,
        final long price,
        final long quantity
    ) {
        return new OrderLineItemCreationRequest(orderType, menuId, BigDecimal.valueOf(price), quantity);
    }
}

