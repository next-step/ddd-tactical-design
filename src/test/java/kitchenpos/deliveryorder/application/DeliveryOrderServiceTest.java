package kitchenpos.deliveryorder.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.deliveryOrder;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.deliveryorder.FakeKitchenridersClient;
import kitchenpos.deliveryorder.InMemoryDeliveryOrderRepository;
import kitchenpos.deliveryorder.domain.DeliveryOrder;
import kitchenpos.deliveryorder.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorder.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorder.domain.DeliveryOrderStatus;
import kitchenpos.deliveryorder.domain.DeliveryOrderType;
import kitchenpos.menu.InMemoryMenuRepository;
import kitchenpos.menu.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class DeliveryOrderServiceTest {
    private DeliveryOrderRepository deliveryOrderRepository;
    private MenuRepository menuRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private DeliveryOrderService deliveryOrderService;

    @BeforeEach
    void setUp() {
        deliveryOrderRepository = new InMemoryDeliveryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        deliveryOrderService = new DeliveryOrderService(
            deliveryOrderRepository,
            menuRepository,
            kitchenridersClient
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final DeliveryOrder expected = createOrderRequest(
            "서울시 송파구 위례성대로 2",
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final DeliveryOrder actual = deliveryOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1),
            () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("deliveryOrderLineItems")
    @ParameterizedTest
    void create(final List<DeliveryOrderLineItem> deliveryOrderLineItems) {
        final DeliveryOrder expected = createOrderRequest(deliveryOrderLineItems);
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutDeliveryOrder(final long quantity) {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct())
        ).getId();
        final DeliveryOrder expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct())
        ).getId();
        final DeliveryOrder expected = createOrderRequest(
            deliveryAddress,
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, false, menuProduct())
        ).getId();
        final DeliveryOrder expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct())
        ).getId();
        final DeliveryOrder expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 16_000L, 3L)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = deliveryOrderRepository.save(deliveryOrder(
            DeliveryOrderStatus.WAITING, "서울시 송파구 위례성대로 2")
        ).getId();
        final DeliveryOrder actual = deliveryOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final DeliveryOrderStatus status) {
        final UUID orderId = deliveryOrderRepository.save(deliveryOrder(status, "서울시 송파구 위례성대로 2"))
            .getId();
        assertThatThrownBy(() -> deliveryOrderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.WAITING, "서울시 송파구 위례성대로 2")
        ).getId();
        final DeliveryOrder actual = deliveryOrderService.accept(orderId);
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
            () -> assertThat(kitchenridersClient.getDeliveryAddress())
                .isEqualTo("서울시 송파구 위례성대로 2")
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.ACCEPTED, "서울시 송파구 위례성대로 2")
        ).getId();
        final DeliveryOrder actual = deliveryOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final DeliveryOrderStatus status) {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(status, "서울시 송파구 위례성대로 2")
        ).getId();
        assertThatThrownBy(() -> deliveryOrderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.SERVED, "서울시 송파구 위례성대로 2")
        ).getId();
        final DeliveryOrder actual = deliveryOrderService.startDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERING);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final DeliveryOrderStatus status) {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(status, "서울시 송파구 위례성대로 2")
        ).getId();
        assertThatThrownBy(() -> deliveryOrderService.startDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.DELIVERING, "서울시 송파구 위례성대로 2")
        ).getId();
        final DeliveryOrder actual = deliveryOrderService.completeDelivery(orderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final DeliveryOrderStatus status) {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(status, "서울시 송파구 위례성대로 2")
        ).getId();
        assertThatThrownBy(() -> deliveryOrderService.completeDelivery(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final DeliveryOrder expected = deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.DELIVERED, "서울시 송파구 위례성대로 2")
        );
        final DeliveryOrder actual = deliveryOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.COMPLETED);
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final DeliveryOrderStatus status) {
        final UUID orderId = deliveryOrderRepository.save(
            deliveryOrder(status, "서울시 송파구 위례성대로 2")
        ).getId();
        assertThatThrownBy(() -> deliveryOrderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.SERVED, "서울시 송파구 위례성대로 2")
        );
        deliveryOrderRepository.save(
            deliveryOrder(DeliveryOrderStatus.DELIVERED, "서울시 송파구 위례성대로 2")
        );
        final List<DeliveryOrder> actual = deliveryOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private DeliveryOrder createOrderRequest(
        final String deliveryAddress,
        final DeliveryOrderLineItem... deliveryOrderLineItems
    ) {
        final DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setType(DeliveryOrderType.DELIVERY);
        deliveryOrder.setDeliveryAddress(deliveryAddress);
        deliveryOrder.setOrderLineItems(Arrays.asList(deliveryOrderLineItems));
        return deliveryOrder;
    }

    private DeliveryOrder createOrderRequest(
        final DeliveryOrderLineItem... deliveryOrderLineItems
    ) {
        return createOrderRequest(Arrays.asList(deliveryOrderLineItems));
    }

    private DeliveryOrder createOrderRequest(
        final List<DeliveryOrderLineItem> deliveryOrderLineItems
    ) {
        final DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setType(DeliveryOrderType.DELIVERY);
        deliveryOrder.setOrderLineItems(deliveryOrderLineItems);
        return deliveryOrder;
    }

    private static List<Arguments> deliveryOrderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    private static DeliveryOrderLineItem createOrderLineItemRequest(
        final UUID menuId,
        final long price,
        final long quantity
    ) {
        final DeliveryOrderLineItem deliveryOrderLineItem = new DeliveryOrderLineItem();
        deliveryOrderLineItem.setSeq(new Random().nextLong());
        deliveryOrderLineItem.setMenuId(menuId);
        deliveryOrderLineItem.setPrice(BigDecimal.valueOf(price));
        deliveryOrderLineItem.setQuantity(quantity);
        return deliveryOrderLineItem;
    }
}
