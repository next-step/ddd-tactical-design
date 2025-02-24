package kitchenpos.order.deliveryorder.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.order;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.ACCEPTED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.COMPLETED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.DELIVERED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.DELIVERING;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.SERVED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import kitchenpos.order.deliveryorder.domain.DeliveryOrder;
import kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus;
import kitchenpos.order.deliveryorder.infra.FakeKitchenridersClient;
import kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DeliveryOrderServiceTest {

    private static final String DEFAULT_ADDRESS = "서울시 송파구 위례성대로 2";

    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private DeliveryOrderService deliveryOrderService;


    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        deliveryOrderService = new DefaultDeliveryOrderService(orderRepository, menuRepository, kitchenridersClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final DeliveryOrder expected = createOrderRequest(
            "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1),
            () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final Order expected = createOrderRequest(DEFAULT_ADDRESS, orderLineItems);
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(
            deliveryAddress, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final Order expected = createOrderRequest(DEFAULT_ADDRESS, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(DEFAULT_ADDRESS, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(WAITING, DEFAULT_ADDRESS)).getId();
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final DeliveryOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, DEFAULT_ADDRESS)).getId();
        assertThatThrownBy(() -> deliveryOrderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID orderId = orderRepository.save(order(WAITING, "서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.accept(orderId);
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(ACCEPTED),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
            () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(ACCEPTED, DEFAULT_ADDRESS)).getId();
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final DeliveryOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, DEFAULT_ADDRESS)).getId();
        assertThatThrownBy(() -> deliveryOrderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final UUID orderId = orderRepository.save(order(SERVED, "서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.startDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(DELIVERING);
    }

    @DisplayName("배달 주문만 배달할 수 있다.")
    @Test
    void startDeliveryWithoutDeliveryOrder() {
        final UUID orderId = orderRepository.save(order(TakeoutOrderStatus.SERVED)).getId();
        assertThatThrownBy(() -> deliveryOrderService.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final DeliveryOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, DEFAULT_ADDRESS)).getId();
        assertThatThrownBy(() -> deliveryOrderService.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final UUID orderId = orderRepository.save(order(DELIVERING, DEFAULT_ADDRESS)).getId();
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.completeDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final DeliveryOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, DEFAULT_ADDRESS)).getId();
        assertThatThrownBy(() -> deliveryOrderService.completeDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Order expected = orderRepository.save(order(DELIVERED, DEFAULT_ADDRESS));
        final DeliveryOrder actual = (DeliveryOrder) deliveryOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(COMPLETED);
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final DeliveryOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, DEFAULT_ADDRESS)).getId();
        assertThatThrownBy(() -> deliveryOrderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    private DeliveryOrder createOrderRequest(
        final String deliveryAddress,
        final OrderLineItem... orderLineItems
    ) {
        return createOrderRequest(deliveryAddress, Arrays.asList(orderLineItems));
    }

    private DeliveryOrder createOrderRequest(
        final String deliveryAddress,
        final List<OrderLineItem> orderLineItems
    ) {
        final DeliveryOrder order = new DeliveryOrder();
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    private static OrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenuId(menuId);
        orderLineItem.setPrice(BigDecimal.valueOf(price));
        orderLineItem.setQuantity(quantity);
        return orderLineItem;
    }
}
