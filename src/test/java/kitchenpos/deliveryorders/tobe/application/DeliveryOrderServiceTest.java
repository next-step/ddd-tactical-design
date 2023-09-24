package kitchenpos.deliveryorders.tobe.application;

import kitchenpos.deliveryorders.tobe.application.dto.DeliveryOrderRequest;
import kitchenpos.deliveryorders.tobe.application.dto.OrderLineItemRequest;
import kitchenpos.deliveryorders.tobe.domain.DeliveryAddress;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrder;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.tobe.infra.FakeKitchenridersClient;
import kitchenpos.deliveryorders.tobe.infra.InMemoryDeliveryOrderRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.sharedkernel.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.*;
import static kitchenpos.deliveryorders.tobe.DeliveryOrderFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class DeliveryOrderServiceTest {
    private DeliveryOrderRepository deliveryOrderRepository;
    private MenuRepository menuRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private DeliveryOrderService sut;

    @BeforeEach
    void setUp() {
        deliveryOrderRepository = new InMemoryDeliveryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        sut = new DeliveryOrderService(deliveryOrderRepository, menuRepository, kitchenridersClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final DeliveryOrderRequest expected = createOrderRequest(
            "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final DeliveryOrder actual = sut.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItemList()).hasSize(1),
            () -> assertThat(actual.getDeliveryAddress()).isEqualTo(new DeliveryAddress(expected.getDeliveryAddress()))
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemRequest> orderLineItems) {
        final DeliveryOrderRequest expected = createOrderRequest(orderLineItems);
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final DeliveryOrderRequest expected = createOrderRequest(createOrderLineItemRequest(menuId, 19_000L, quantity));
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final DeliveryOrderRequest expected = createOrderRequest(deliveryAddress, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final DeliveryOrderRequest expected = createOrderRequest(createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final DeliveryOrderRequest expected = createOrderRequest(createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(deliveryOrder(menu)).getId();
        final DeliveryOrder actual = sut.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void acceptFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(acceptedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(deliveryOrder(menu)).getId();
        final DeliveryOrder actual = sut.accept(orderId);
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
            () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(acceptedOrder(menu)).getId();
        final DeliveryOrder actual = sut.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serveFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(servedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(servedOrder(menu)).getId();
        final DeliveryOrder actual = sut.startDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @Test
    void startDeliveryFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(deliveredOrder(menu)).getId();
        assertThatThrownBy(() -> sut.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(deliveringOrder(menu)).getId();
        final DeliveryOrder actual = sut.completeDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @Test
    void completeDeliveryFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(deliveredOrder(menu)).getId();
        assertThatThrownBy(() -> sut.completeDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Menu menu = menuRepository.save(menu());
        final DeliveryOrder expected = deliveryOrderRepository.save(deliveredOrder(menu));
        final DeliveryOrder actual = sut.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("배달 주문은 배달 완료된 주문만 완료할 수 있다.")
    @Test
    void completeDeliveryOrder() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = deliveryOrderRepository.save(completedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        deliveryOrderRepository.save(deliveryOrder("서울시 양재구 남부순환로 3"));
        deliveryOrderRepository.save(deliveryOrder());
        final List<DeliveryOrder> actual = sut.findAll();
        assertThat(actual).hasSize(2);
    }

    private DeliveryOrderRequest createOrderRequest(
        final String deliveryAddress,
        final OrderLineItemRequest... orderLineItems
    ) {
        return new DeliveryOrderRequest(Arrays.asList(orderLineItems), deliveryAddress);
    }

    private DeliveryOrderRequest createOrderRequest(final OrderLineItemRequest... orderLineItems) {
        return new DeliveryOrderRequest(Arrays.asList(orderLineItems), "주소");
    }

    private DeliveryOrderRequest createOrderRequest(final List<OrderLineItemRequest> orderLineItems) {
        return new DeliveryOrderRequest(orderLineItems, "주소");
    }

    private static OrderLineItemRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemRequest(
            quantity,
            menuId,
            price
        );
    }
}
